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
import projectvantage.utility.LogConfig;
import projectvantage.utility.ElementConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
    
    LogConfig logConf = new LogConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    AuthenticationConfig authConf = new AuthenticationConfig();
    dbConnect db = new dbConnect();
    ElementConfig elementConf = new ElementConfig();
    
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    private String username;
    private String password;
    private String salt;
    private int userId;
    
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
    @FXML
    private TextField revealedNewPasswordField;
    @FXML
    private TextField revealedConfirmPasswordField;
    @FXML
    private ImageView hideNewPasswordButton;
    @FXML
    private ImageView showNewPasswordButton;
    @FXML
    private ImageView hideConfirmPasswordButton;
    @FXML
    private ImageView showConfirmPasswordButton;

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
        userId = user.getId();
    }
    
//    private void returnToLogin(Event event) throws Exception {
//        pageConf.switchScene(getClass(), event, "/projectvantage/fxml/authentication/Login.fxml");
//    }
    
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
        
        if(db.executeQuery(sql, updatedPassword, username)) {
            System.out.println("User updated successfully!");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Change Password Successful", "Password Changed Succesfully!", currentStage);
            logConf.logResetPassword(userId);
            currentStage.close();
        }
    }

    @FXML
    private void cancelButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void hideNewPasswordButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(hideNewPasswordButton);
    }

    @FXML
    private void hideNewPasswordButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(hideNewPasswordButton);
    }

    @FXML
    private void hideNewPasswordButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(hideNewPasswordButton);
    }

    @FXML
    private void hideNewPasswordButtonMouseClickHandler(MouseEvent event) {
        hideNewPasswordButton.setVisible(false);
        showNewPasswordButton.setVisible(true);
        
        revealedNewPasswordField.setOpacity(1.0);
        revealedNewPasswordField.toFront();
        newPasswordField.setOpacity(0);
    }

    @FXML
    private void hideNewPasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(hideNewPasswordButton);
    }

    @FXML
    private void showNewPasswordButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(showNewPasswordButton);
    }

    @FXML
    private void showNewPasswordButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(showNewPasswordButton);
    }

    @FXML
    private void showNewPasswordButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(showNewPasswordButton);
    }

    @FXML
    private void showNewPaswordButtonMouseClickHandler(MouseEvent event) {
        showNewPasswordButton.setVisible(false);
        hideNewPasswordButton.setVisible(true);
        
        newPasswordField.setOpacity(1.0);
        newPasswordField.toFront();
        revealedNewPasswordField.setOpacity(0);
    }

    @FXML
    private void showNewPasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(showNewPasswordButton);
    }

    @FXML
    private void hideConfirmPasswordButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(hideConfirmPasswordButton);
    }

    @FXML
    private void hideConfirmPasswordButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(hideConfirmPasswordButton);
    }

    @FXML
    private void hideConfirmPasswordButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(hideConfirmPasswordButton);
    }

    @FXML
    private void hideConfirmPasswordButtonMouseClickHandler(MouseEvent event) {
        hideConfirmPasswordButton.setVisible(false);
        showConfirmPasswordButton.setVisible(true);
        
        revealedConfirmPasswordField.setOpacity(1.0);
        revealedConfirmPasswordField.toFront();
        confirmPasswordField.setOpacity(0);
    }

    @FXML
    private void hideConfirmPasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(hideConfirmPasswordButton);
    }

    @FXML
    private void showConfirmPasswordButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(showConfirmPasswordButton);
    }

    @FXML
    private void showConfirmPasswordButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(showConfirmPasswordButton);
    }

    @FXML
    private void showConfirmPasswordButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(showConfirmPasswordButton);
    }

    @FXML
    private void showConfirmPaswordButtonMouseClickHandler(MouseEvent event) {
        showConfirmPasswordButton.setVisible(false);
        hideConfirmPasswordButton.setVisible(true);
        
        confirmPasswordField.setOpacity(1.0);
        confirmPasswordField.toFront();
        revealedConfirmPasswordField.setOpacity(0);
    }

    @FXML
    private void showConfirmPasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(showConfirmPasswordButton);
    }

    @FXML
    private void newPasswordFieldKeyTypeHandler(KeyEvent event) {
        String passField = newPasswordField.getText();
        revealedNewPasswordField.textProperty().bindBidirectional(newPasswordField.textProperty());
        
        hideNewPasswordButton.setVisible(false);
        
        if(!passField.isEmpty()) 
            hideNewPasswordButton.setVisible(true);
    }

    @FXML
    private void confirmPasswordFieldKeyTypedHandler(KeyEvent event) {
        String passField = confirmPasswordField.getText();
        revealedConfirmPasswordField.textProperty().bindBidirectional(confirmPasswordField.textProperty());
        
        hideConfirmPasswordButton.setVisible(false);
        
        if(!passField.isEmpty()) 
            hideConfirmPasswordButton.setVisible(true);
    }
    
}
