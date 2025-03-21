/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.authentication;

import projectvantage.utility.Config;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.PageConfig;
import projectvantage.controllers.team_member.TeamMemberMainPageController;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.utility.AuthenticationConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.controllers.misc.AuthenticationController;
import projectvantage.utility.AlertConfig;

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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class LoginController implements Initializable {
    
    Config config = new Config();
    PageConfig pageConf = new PageConfig();
    ElementConfig elementConf = new ElementConfig();
    AuthenticationConfig authConfig = new AuthenticationConfig();
    AdminPageController adminController = AdminPageController.getInstance();
    DatabaseConfig dbConf = new DatabaseConfig();
    dbConnect db = new dbConnect();
    AlertConfig alertConf = new AlertConfig();
    AuthenticationController authController = new AuthenticationController();
    
    private static LoginController instance;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private Pane titlePane;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Rectangle exitButtonBG;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label passwordResetButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }
    
    public static LoginController getInstance() {
        return instance;
    }
    
    private boolean findUsername(String user) {
        try(ResultSet result = db.getData("SELECT username FROM user  WHERE username = '" + user + "'")) {
            if(result.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }
    
    private void loginUser(Event event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String username = usernameField.getText();
        String password = passwordField.getText();
        String userRole = dbConf.getRole(username);
        String userPassword = dbConf.getPassword(username);
        String salt = dbConf.getSalt(username);
        String email = dbConf.getEmail(username);
        int userId = dbConf.getUserId(username);

        
        if(username.isEmpty()) {
            alertConf.showLoginErrorAlert(currentStage, "Username must not be empty.");
            return;
        }
        
        if (password.isEmpty()) {
            alertConf.showLoginErrorAlert(currentStage, "Passowrd must not be empty.");
            return;
        }
        
        boolean isUsernameFound = findUsername(username);
        
        if(!isUsernameFound) {
            alertConf.showLoginErrorAlert(currentStage, "Username not found.");
            return;
        }
        
        boolean doesPasswordMatch = authConfig.verifyPassword(password, userPassword, salt);
        
        if(!doesPasswordMatch) {
            alertConf.showLoginErrorAlert(currentStage, "Password does not match.");
            return;
        }
        
        boolean isStatusActive = dbConf.getStatus(username).equals("active");
        
        if(!isStatusActive) {
            alertConf.showLoginErrorAlert(currentStage, "Account is not active yet.");
            return;
        }
        
        alertConf.showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Successfully Logged In", currentStage);
        
                
        switch(userRole) {
            case "team member":
                switchScene(getClass(), event, "/projectvantage/fxml/team_member/TeamMemberMainPage.fxml");
                break;
            case "admin":
                switchScene(getClass(),event, "/projectvantage/fxml/admin/AdminPage.fxml");
                break;
            default:
                alertConf.showLoginErrorAlert(currentStage, "Role not found.");
        }
        alertConf.showAlert(Alert.AlertType.INFORMATION, "Login Alert", "Welcome " + username + "!", currentStage);
    }
    
    public void switchScene(Class getClass, Event evt, String targetFXML) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass.getResource(targetFXML));
        Parent root = loader.load();
        
        Stage currentStage = (Stage) titlePane.getScene().getWindow();
        
        String user = usernameField.getText();
        String userRole = dbConf.getRole(user);
        
        String teamMemberPageFXML = "/projectvantage/fxml/team_member/TeamMemberDashboardPage.fxml";
        String adminPageFXML = "/projectvantage/fxml/admin/AdminDashboardPage.fxml";
        
        switch(userRole){
            case "team member":
                TeamMemberMainPageController teamMemberController = loader.getController();
                teamMemberController.setUsername(user);
                pageConf.loadDashboardPage(teamMemberPageFXML, user, teamMemberController.getBackgroundPane(), teamMemberController.getRootPane());
            break;
            case "admin":
                AdminPageController adminController = loader.getController();
                adminController.setUsername(user);
                pageConf.loadDashboardPage(adminPageFXML, user, adminController.getBackgroundPane(), adminController.getRootPane());
            break;
        }
        
        Stage stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        pageConf.setCenterAlignment(stage);
        stage.show();
    }
    
    private boolean isEnterPressed(KeyEvent event) throws Exception {
        return event.getCode() == KeyCode.ENTER;
    }
    
    private boolean isTabPressed(KeyEvent event) throws Exception {
        return event.getCode() == KeyCode.TAB;
    }
    
    @FXML
    private void registerButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        String registerFXML = "/projectvantage/fxml/authentication/Register.fxml";
        
        pageConf.switchScene(getClass(), event, registerFXML);
        currentStage.setTitle("Register");
    }

    @FXML
    private void loginButtonMouseClickHandler(MouseEvent event) throws Exception {
        loginUser(event);
    }

    @FXML
    private void usernameFieldOnKeyPressedHandler(KeyEvent event) throws Exception {
        if(isTabPressed(event)) {
            passwordField.setFocusTraversable(true);
        }
        
        if(isEnterPressed(event))
            loginUser(event);
    }

    @FXML
    private void registerFieldOnKeyPressedHandler(KeyEvent event) throws Exception{
        if(isEnterPressed(event))
            loginUser(event);
    }

    @FXML
    private void rootPaneKeyPressedHandler(KeyEvent event) throws Exception {
        if(isTabPressed(event)) {
            usernameField.setFocusTraversable(true);
        }
    }

    @FXML
    private void passwordResetButtonMouseClickHandler(MouseEvent event) {
        
    }
    
}
