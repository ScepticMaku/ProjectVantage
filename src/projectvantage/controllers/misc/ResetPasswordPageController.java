/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.AuthenticationConfig;
import projectvantage.utility.PageConfig;
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
    
    AuthenticationConfig authConf = new AuthenticationConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    dbConnect db = new dbConnect();
    
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    private String username;
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
        salt = user.getSalt();
        
        usernamePlaceholder.setText(username);
    }
    
    private void returnToLogin(Event event) throws Exception {
        pageConf.switchScene(getClass(), event, "/projectvantage/fxml/authentication/Login.fxml");
    }
    
    private boolean checkInput(Stage stage, String pass, String confirmPass) {
        
        if(pass.isEmpty()) {
            alertConf.showChangePasswordErrorAlert(stage, "New password field must not be empty.");
            return true;
        }
        
        if(confirmPass.isEmpty()) {
            alertConf.showChangePasswordErrorAlert(stage, "Confirm password field must not be empty.");
            return true;
        }
        
        if(pass.length() < MINIMUM_PASSWORD_LENGTH) {
            alertConf.showChangePasswordErrorAlert(stage, "Password must be at least 8 characters.");
            return true;
        }
        return false;
    }


    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String password = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        String newPass = authConf.hashPassword(confirmPassword, salt);
        
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        
        if(checkInput(currentStage, password, confirmPassword))
            return;
        
        if(db.updateData(sql, newPass, username)) {
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
