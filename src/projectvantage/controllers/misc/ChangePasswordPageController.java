/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.dbConnect;
import projectvantage.utility.Config;
import projectvantage.utility.PageConfig;
import projectvantage.utility.AuthenticationConfig;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.controllers.team_member.TeamMemberMainPageController;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class ChangePasswordPageController implements Initializable {
    
    private static ChangePasswordPageController instance;
    
    dbConnect db = new dbConnect();
    Config config = new Config();
    PageConfig pageConf = new PageConfig();
    AuthenticationConfig authConfig = new AuthenticationConfig();
    
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    private String username;
    private String role;

    @FXML
    private Button backButton;
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
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getUsername() {
        return username;
    }
    
    private void returnToPreviousPage() {
        String fxmlLocation = "/projectvantage/fxml/misc/ProfilePage.fxml";
        TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
        AdminPageController adminController = AdminPageController.getInstance();
        
        switch(role) {
            case "team member":
                pageConf.loadProfilePage(fxmlLocation, username, teamMemberController.getBackgroundPane(), teamMemberController.getRootPane());
                teamMemberController.getTitlebarLabel().setText("Profile");
                break;
            case "admin":
                pageConf.loadProfilePage(fxmlLocation, username, adminController.getBackgroundPane(), adminController.getRootPane());
                adminController.getTitlebarLabel().setText("Profile");
                break;
        }
    }
    
    private String getPassword(String user) {
        try(ResultSet result = db.getData("SELECT password FROM user WHERE username = '" + user + "'")) {
            if(result.next()) {
                return result.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    public String getSalt(String user) {
        dbConnect db = new dbConnect();
        try(ResultSet result = db.getData("SELECT salt FROM user WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("salt");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    private boolean checkFields(Stage currentStage, String currentField, String newField, String confirmField) {
        
        if(currentField.isEmpty()) {
            config.showErrorMessage("Current password field must not be empty", "Password Error", currentStage);
            return true;
        }
        
        if(newField.length() < MINIMUM_PASSWORD_LENGTH) {
            config.showErrorMessage("Password must be at least 8 characters.", "Password Error", currentStage);
            return true;
        }
        
        if(newField.isEmpty()) {
            config.showErrorMessage("New password field must not be empty", "Password Error", currentStage);
            return true;
        }
        
        if(confirmField.isEmpty()) {
            config.showErrorMessage("Confirm password field must not be empty", "Password Error", currentStage);
            return true;
        }
        
        String userPassword = getPassword(username);
        String salt = getSalt(username);
        
        boolean doesPasswordMatch = authConfig.verifyPassword(currentField, userPassword, salt);
        
        if(!doesPasswordMatch) {
            config.showErrorMessage("Password does not match.", "Login Error", currentStage);
            return true;
        }
        
//        if(!getPassword(username).equals(currentField)) {
//            config.showErrorMessage("Incorrect password", "Password Error", currentStage);
//            return true;
//        }
        
        if(!confirmField.equals(newField)){
            config.showErrorMessage("Passwords does not match", "Password Error", currentStage);
            return true;
        }
        return false;
    }
    
    

    @FXML
    private void backButtonMouseClickHandler(MouseEvent event) {
        returnToPreviousPage();
    }

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String salt = getSalt(username);
        
        String currentF = currentPasswordField.getText();
        String newF = newPasswordField.getText();
        String confirmF = confirmPasswordField.getText();
        
        String newPass = authConfig.hashPassword(newPasswordField.getText(), salt);
        
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        
        if(checkFields(currentStage, currentF , newF , confirmF))
            return;
        
        if(db.updateData(sql, newPass, username)) {
            System.out.println("User updated successfully!");
            config.showAlert(Alert.AlertType.INFORMATION, "Change Password", "Password Changed Succesfully!", currentStage);
            returnToPreviousPage();
        }
    }
    
}
