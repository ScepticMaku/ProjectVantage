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
    
}
