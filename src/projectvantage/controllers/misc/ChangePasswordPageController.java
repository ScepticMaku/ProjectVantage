/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.dbConnect;
import projectvantage.utility.Config;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.AuthenticationConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;
import projectvantage.utility.LogConfig;
import projectvantage.utility.ElementConfig;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.controllers.authentication.LoginController;
import projectvantage.controllers.team_member.TeamMemberMainPageController;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class ChangePasswordPageController implements Initializable {
    
    private static ChangePasswordPageController instance;
    
    AlertConfig alertConf = new AlertConfig();
    dbConnect db = new dbConnect();
    Config config = new Config();
    PageConfig pageConf = new PageConfig();
    AuthenticationConfig authConf = new AuthenticationConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    LogConfig logConf = new LogConfig();
    ElementConfig elementConf = new ElementConfig();
    
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    private int userId;
    private String password;
    private String salt;
    private String username;
    private String role;

    @FXML
    private TextField currentPasswordField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private Button submitButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label usernamePlaceholder;
    @FXML
    private TextField revealedCurrentPasswordField;
    @FXML
    private TextField revealedNewPasswordField;
    @FXML
    private TextField revealedConfirmPasswordField;
    @FXML
    private ImageView hideCurrentPasswordButton;
    @FXML
    private ImageView showCurrentPasswordButton;
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
    
    public static ChangePasswordPageController getInstance() {
        return instance;
    }
    
    public void setUsername(String userInput) {
        
        User user = dbConf.getUserByUsername(userInput);
        
        username = user.getUsername();
        role = user.getRole();
        password = user.getPassword();
        salt = user.getSalt();
        userId = user.getId();
        
        usernamePlaceholder.setText(userInput);
    }
    
    public void switchScene(Class getClass, Event evt, String targetFXML) throws Exception {
        Stage stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass.getResource(targetFXML));
        Parent root = loader.load();
        
        String fxmlLocation = "/projectvantage/fxml/misc/ProfilePage.fxml";
        
//        switch(role){
//            case "team member":
//                TeamMemberMainPageController teamMemberController = loader.getController();
//                teamMemberController.setUsername(username);
//                pageConf.loadProfilePage(fxmlLocation, username, teamMemberController.getBackgroundPane(), teamMemberController.getRootPane());
//            break;
//            case "admin":
//                AdminPageController adminController = loader.getController();
//                adminController.setUsername(username);
//                pageConf.loadProfilePage(fxmlLocation, username, adminController.getBackgroundPane(), adminController.getRootPane());
//            break;
//        }
        
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        pageConf.setCenterAlignment(stage);
        stage.show();
    }
    
    private boolean checkFields(Stage currentStage, String currentField, String newField, String confirmField) {
        
        if(currentField.isEmpty()) {
            alertConf.showChangePasswordErrorAlert(currentStage, "Current password field must not be empty.");
            return true;
        }
        
        if(newField.isEmpty()) {
            alertConf.showChangePasswordErrorAlert(currentStage, "New password field must not be empty.");
            return true;
        }
        
        if(confirmField.isEmpty()) {
            alertConf.showChangePasswordErrorAlert(currentStage, "Confirm password field must not be empty.");
            return true;
        }
        
        if(newField.length() < MINIMUM_PASSWORD_LENGTH) {
            alertConf.showChangePasswordErrorAlert(currentStage, "Password must be at least 8 characters.");
            return true;
        }
        
        boolean isSamePassword = currentField.equals(newField);
        
        if(isSamePassword) {
            alertConf.showChangePasswordErrorAlert(currentStage, "Must not be an old password.");
        }
        
        boolean doesPasswordMatch = authConf.verifyPassword(currentField, password, salt);
        
        if(!doesPasswordMatch) {
            alertConf.showChangePasswordErrorAlert(currentStage, "Password doesn't exists.");
            return true;
        }
        
        if(!confirmField.equals(newField)){
            alertConf.showChangePasswordErrorAlert(currentStage, "Password does not match.");
            return true;
        }
        return false;
    }

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String currentF = currentPasswordField.getText();
        String newF = newPasswordField.getText();
        String confirmF = confirmPasswordField.getText();
        
        String newPass = authConf.hashPassword(confirmF, salt);
        
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        
        if(checkFields(currentStage, currentF , newF , confirmF))
            return;
        
        if(db.executeQuery(sql, newPass, username)) {
            System.out.println("User updated successfully!");
            logConf.logResetPassword(userId);
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Change Password Successful", "Password Changed Succesfully!", currentStage);
            
            currentStage.close();
        }
    }

    @FXML
    private void hideCurrentPasswordButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(hideCurrentPasswordButton);
    }

    @FXML
    private void hideCurrentPasswordButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(hideCurrentPasswordButton);
    }

    @FXML
    private void hideCurrentPasswordButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(hideCurrentPasswordButton);
    }

    @FXML
    private void hideCurrentPasswordButtonMouseClickHandler(MouseEvent event) {
        hideCurrentPasswordButton.setVisible(false);
        showCurrentPasswordButton.setVisible(true);
        
        revealedCurrentPasswordField.setOpacity(1.0);
        revealedCurrentPasswordField.toFront();
        currentPasswordField.setOpacity(0);
    }

    @FXML
    private void hideCurrentPasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(hideCurrentPasswordButton);
    }

    @FXML
    private void showCurrentPasswordButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(showCurrentPasswordButton);
    }

    @FXML
    private void showCurrentPasswordButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(showCurrentPasswordButton);
    }

    @FXML
    private void showCurrentPasswordButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(showCurrentPasswordButton);
    }

    @FXML
    private void showCurrentPaswordButtonMouseClickHandler(MouseEvent event) {
        showCurrentPasswordButton.setVisible(false);
        hideCurrentPasswordButton.setVisible(true);
        
        currentPasswordField.setOpacity(1.0);
        currentPasswordField.toFront();
        revealedCurrentPasswordField.setOpacity(0);
    }

    @FXML
    private void showCurrentPasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(showCurrentPasswordButton);
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
    private void currentPasswordFieldKeyTypedHandler(KeyEvent event) {
        String passField = currentPasswordField.getText();
        revealedCurrentPasswordField.textProperty().bindBidirectional(currentPasswordField.textProperty());
        
        hideCurrentPasswordButton.setVisible(false);
        
        if(!passField.isEmpty()) 
            hideCurrentPasswordButton.setVisible(true);
    }

    @FXML
    private void newPasswordFieldKeyTypedHandler(KeyEvent event) {
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
