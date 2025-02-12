/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.authentication;

import projectvantage.utility.Config;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import projectvantage.utility.dbConnect;
import java.sql.*;
import javafx.event.Event;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label registerButton;
    @FXML
    private Button loginButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    Config config = new Config();
    
    private boolean locateUser(String user, String pass) {
        dbConnect db = new dbConnect();
        try(ResultSet result = db.getData("SELECT username, password FROM user  WHERE username = '" + user + "' AND password = '" + pass + "'")) {
            return result.next();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }
    
    private String getRole(String user, String pass) {
        dbConnect db = new dbConnect();
        try(ResultSet result = db.getData("SELECT role FROM user WHERE username = '" + user + "' AND password = '" + pass + "'")) {
            if(result.next()) {
                String role = result.getString("role");
                return role;
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    private String getStatus(String user, String pass) {
        dbConnect db = new dbConnect();
        try(ResultSet result = db.getData("SELECT status FROM user WHERE username = '" + user + "' AND password = '" + pass + "'")) {
            if(result.next()) {
                String status = result.getString("status");
                return status;
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    @FXML
    private void registerButtonMouseClickHandler(MouseEvent event) throws Exception {
        AuthenticationController authControl = AuthenticationController.getInstance();
        
        if(authControl != null) {
            Pane loginPane = authControl.getLoginPane();
            Pane otherPane = authControl.getOtherPane();
            Pane title = authControl.getTitlePane();
            
            loginPane.setVisible(false);
            otherPane.setVisible(true);
            title.setLayoutY(74);
        }
    }

    @FXML
    private void loginButtonMouseClickHandler(MouseEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if(username.isEmpty()) {
            config.showErrorMessage("Username must not be empty.", "Login error");
            return;
        } else if (password.isEmpty()) {
            config.showErrorMessage("Password must not be empty.", "Login error");
            return;
        }
        
        if(username.equals("super") && password.equals("1234")) {
            config.switchScene(getClass(), event, "/projectvantage/fxml/superadmin/SuperAdminPage.fxml");
            return;
        }
    
        if(!locateUser(username, password)) {
            config.showErrorMessage("Username not found.", "Login error");
            return;
        }
        
        if(getStatus(username, password).equals("inactive")) {
            config.showErrorMessage("Your account isn't active yet.", "Account Status Error");
            return;
        }
        
        switch(getRole(username, password)) {
            case "team member":
                config.switchScene(getClass(), event, "/projectvantage/fxml/team_member/TeamMemberMainPage.fxml");
                break;
            case "admin":
                config.switchScene(getClass(), event, "/projectvantage/fxml/admin/MainPage.fxml");
                break;
        }
    }
    
}
