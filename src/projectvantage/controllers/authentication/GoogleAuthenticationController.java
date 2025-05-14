/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.authentication;

import projectvantage.utility.Config;
import projectvantage.utility.SessionConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.GoogleAuthenticationConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.LogConfig;
import projectvantage.utility.SessionConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectvantage.controllers.misc.SettingsPageController;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class GoogleAuthenticationController implements Initializable {
    
    private static GoogleAuthenticationController instance;
    
    dbConnect connect = new dbConnect();
    PageConfig pageConf = new PageConfig();
    Config config = new Config();
    dbConnect db = new dbConnect();
    GoogleAuthenticationConfig googleAuthConf = new GoogleAuthenticationConfig();
    AlertConfig alertConf = new AlertConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    LogConfig logConf = new LogConfig();
    
    private String email;
    private String secretKey;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView qrImageView;
    @FXML
    private Button submitButton;
    @FXML
    private TextField otpField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            SessionConfig sessionConf = SessionConfig.getInstance();
            
            email = sessionConf.getEmail();
            
            secretKey = googleAuthConf.generateSecretKey();
            String issuer = "ProjectVantage";

            googleAuthConf.generateQRCode(secretKey, email, issuer, qrImageView);
            
        });
    }
    
    public static GoogleAuthenticationController getInstance() {
        return instance;
    }
    
    public void loadContent(String sql, String...info) {
    }
    
    private boolean isEnterPressed(KeyEvent event) throws Exception {
        return event.getCode() == KeyCode.ENTER;
    }
    
    private void verifyUser(Event event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        String OTP = otpField.getText();
        
        if(OTP.isEmpty()) {
            alertConf.showAuthenticationErrorAlert(currentStage, "You must enter a verification code.");
            return;
        }
        
        if(!googleAuthConf.verifyOTP(currentStage, OTP, secretKey)) {
            alertConf.showAuthenticationErrorAlert(currentStage, "Incorrect verification code.");
            return;
        }
        
        // Update the database with the secret key
        String updateQuery = "UPDATE user SET secret_key = ? WHERE email = ?";
        if(db.executeQuery(updateQuery, secretKey, email)) {
            // Update session
            SessionConfig sessionConf = SessionConfig.getInstance();
            sessionConf.setSecretKey(secretKey);
            
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Authentication Successful", "Two-factor authentication has been enabled successfully!", currentStage);
            
            // Close the current window and refresh settings page
            currentStage.close();
            
            // Get settings page controller and refresh content
            SettingsPageController settingsController = SettingsPageController.getInstance();
            if(settingsController != null) {
                settingsController.load();
            }
        }
    }

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) throws Exception {
        verifyUser(event);
    }

    @FXML
    private void otpFieldKeyPressedHandler(KeyEvent event) throws Exception {
        if(isEnterPressed(event))
            verifyUser(event);
    }
}
