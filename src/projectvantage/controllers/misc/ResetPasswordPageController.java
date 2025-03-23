/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.AuthenticationConfig;
import projectvantage.utility.dbConnect;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class ResetPasswordPageController implements Initializable {
    
    private static ResetPasswordPageController instance;
    
    DatabaseConfig dbConf = new DatabaseConfig();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    AuthenticationConfig authConf = new AuthenticationConfig();
    dbConnect db = new dbConnect();
    
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    private String username;
    private String password;
    private String salt;

    @FXML
    private Label usernamePlaceholder;
    @FXML
    private Button submitButton;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }    
    
    public static ResetPasswordPageController getInstance() {
        return instance;
    }
    
    public void setUsername(String userInput) {
        
        User user = dbConf.getUserByUsername(userInput);
        
        username = user.getUsername();
        
        usernamePlaceholder.setText(username);
        password = user.getPassword();
        salt = user.getSalt();
    }
    
    private void returnToLogin(Event event) throws Exception {
        pageConf.switchScene(getClass(), event, "/projectvantage/fxml/authentication/Login.fxml");
    }
    
    private boolean verifyInput(Stage stage, String newPassword, String confirmPassword) throws Exception {
        
        if(newPassword.isEmpty()) {
            alertConf.showResetPasswordErrorAlert(stage, "New password must not be empty.");
            return true;
        }
        
        if(confirmPassword.isEmpty()) {
            alertConf.showResetPasswordErrorAlert(stage, "Confirm password must not be empty.");
            return true;
        }
        
        if(newPassword.length() < MINIMUM_PASSWORD_LENGTH) {
            alertConf.showChangePasswordErrorAlert(stage, "Password must be at least 8 characters.");
            return true;
        }
        
        String hashedConfirmPassword = authConf.hashPassword(confirmPassword, salt);
        
        boolean isOldPassword = hashedConfirmPassword.equals(password);
        
        if(isOldPassword) {
            alertConf.showChangePasswordErrorAlert(stage, "Must not be an old password.");
            return true;
        }
        return false;
    }


    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        if(verifyInput(currentStage, newPassword, confirmPassword)) {
            return;
        }
        
        String updatedPassword = authConf.hashPassword(confirmPassword, salt);
        
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        
        if(verifyInput(currentStage , newPassword , confirmPassword))
            return;
        
        if(db.updateData(sql, updatedPassword, username)) {
            System.out.println("User updated successfully!");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Change Password Successful", "Password Changed Succesfully!", currentStage);
            
            returnToLogin(event);
        }
    }

    @FXML
    private void cancelButtonMouseClickHandler(MouseEvent event) throws Exception {
        returnToLogin(event);
    }
    
}
