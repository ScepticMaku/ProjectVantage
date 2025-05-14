/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.SessionConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.Config;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class SettingsPageController implements Initializable {
    
    private static SettingsPageController instance;

    PageConfig pageConf = new PageConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    dbConnect db = new dbConnect();
    Config config = new Config();
    
    private String emailAddress;
    private String phoneNumber;
    private String role;
    private String secretKey;
    
    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private Button logsButton;
    @FXML
    private Pane adminSection;
    @FXML
    private Label authenticationLabel;
    @FXML
    private Button enableAuthenticationButton;
    @FXML
    private Button disableAuthenticationButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private Button applyChangesButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        // Add listeners to text fields
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkForChanges();
        });
        
        phoneNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkForChanges();
        });
        
        // Initially hide the apply changes button
        applyChangesButton.setVisible(false);
        
        load();
    }    
    
    public static SettingsPageController getInstance() {
        return instance;
    }
    
    public void load() {
        Platform.runLater(() -> {
            
            SessionConfig sessionConf = SessionConfig.getInstance();
            
            role = sessionConf.getRole();
            emailAddress = sessionConf.getEmail();
            phoneNumber = sessionConf.getPhoneNumber();
            secretKey = sessionConf.getSecretKey();
            
            if(!role.equals("admin")) {
                adminSection.setOpacity(0.0);
            }
            loadContent();
        });
    }
    
    public void loadContent() {
        emailTextField.setText(emailAddress);
        phoneNumberTextField.setText(phoneNumber);
        
        if(secretKey != null) {
            authenticationLabel.setText("Enabled");
            disableAuthenticationButton.setVisible(true);
            enableAuthenticationButton.setVisible(false);
        }
    }
    
    @FXML
    private void logsButtonMouseClicklHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/admin/EventLogPage.fxml", "Event Logs",backgroundPane);
    }

    @FXML
    private void enableAuthenticationButtonMouseClickHandler(MouseEvent event) throws Exception {
        SessionConfig sessionConf = SessionConfig.getInstance();
        String email = sessionConf.getEmail();
        String number = sessionConf.getPhoneNumber();
        
        pageConf.loadWindow("/projectvantage/fxml/authentication/GoogleAuthentication.fxml", "Enable 2FA", backgroundPane);
    }

    @FXML
    private void disableAuthenticationButtonMouseClickHandler(MouseEvent event) {
        SessionConfig sessionConf = SessionConfig.getInstance();
        DatabaseConfig dbConf = new DatabaseConfig();
        AlertConfig alertConf = new AlertConfig();
        
        String username = sessionConf.getUsername();
        
        // Update the database to set secret_key to null
        String query = "UPDATE user SET secret_key = NULL WHERE username = ?";
        if(db.executeQuery(query, username)) {
            // Update session
            sessionConf.setSecretKey(null);
            
            // Show success message
            Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
            alertConf.showAlert(Alert.AlertType.INFORMATION, "2FA Disabled", "Two-factor authentication has been disabled successfully.", currentStage);
            
            // Update UI
            authenticationLabel.setText("Disabled");
            disableAuthenticationButton.setVisible(false);
            enableAuthenticationButton.setVisible(true);
        }
    }

    private void checkForChanges() {
        boolean hasChanges = !emailTextField.getText().equals(emailAddress) || 
                           !phoneNumberTextField.getText().equals(phoneNumber);
        applyChangesButton.setVisible(hasChanges);
    }

    @FXML
    private void applyChangesButtonMouseClickHandler(MouseEvent event) throws Exception {
        String newEmail = emailTextField.getText().trim();
        String newPhone = phoneNumberTextField.getText().trim();
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        AlertConfig alertConf = new AlertConfig();
        
        // Validate using existing Config methods
        if(newEmail.isEmpty()) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Email address cannot be empty.", currentStage);
            return;
        }
        
        if(!config.isValidEmailFormat(newEmail)) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Email format is invalid.", currentStage);
            return;
        }
        
        if(newPhone.isEmpty()) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Phone number cannot be empty.", currentStage);
            return;
        }
        
        if(config.isValidPhoneNumber(newPhone)) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Phone number is invalid.", currentStage);
            return;
        }
        
        if(!config.isValidPhoneNumberFormat(newPhone)) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Phone number format is invalid.", currentStage);
            return;
        }
        
        // Check if email already exists (only if it's different from current email)
        if(!newEmail.equals(emailAddress) && config.isDuplicated("email", newEmail)) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Email already exists.", currentStage);
            return;
        }
        
        // Update database
        String query = "UPDATE user SET email = ?, phone_number = ? WHERE email = ?";
        if(db.executeQuery(query, newEmail, newPhone, emailAddress)) {
            // Update session
            SessionConfig sessionConf = SessionConfig.getInstance();
            sessionConf.setEmail(newEmail);
            sessionConf.setPhoneNumber(newPhone);
            
            // Update local variables
            emailAddress = newEmail;
            phoneNumber = newPhone;
            
            // Hide apply button
            applyChangesButton.setVisible(false);
            
            // Show success message
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Success", "Your information has been updated successfully.", currentStage);
        } else {
            // Show error message
            alertConf.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update your information. Please try again.", currentStage);
        }
    }
    
}
