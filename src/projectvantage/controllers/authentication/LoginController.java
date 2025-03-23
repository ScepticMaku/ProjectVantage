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
import projectvantage.models.User;

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
//    AdminPageController adminController = AdminPageController.getInstance();
    DatabaseConfig dbConf = new DatabaseConfig();
    dbConnect db = new dbConnect();
    AlertConfig alertConf = new AlertConfig();
    AuthenticationController authController = new AuthenticationController();
    
    private static LoginController instance;
    
    private String role;
    private String password;
    private String username;
    private String salt;
    private String status;
    private String email;
    

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
    
    private void loginUser(Event event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String userInput = usernameField.getText();
        String passwordInput = passwordField.getText();
        
        if(userInput.isEmpty()) {
            alertConf.showLoginErrorAlert(currentStage, "Username must not be empty.");
            return;
        }
        
        if (passwordInput.isEmpty()) {
            alertConf.showLoginErrorAlert(currentStage, "Password must not be empty.");
            return;
        }
        
        User user = dbConf.getUserByUsername(userInput);
        
        if(user == null) {
            alertConf.showLoginErrorAlert(currentStage, "Username not found.");
            return;
        }
        
        role= user.getRole();
        password = user.getPassword();
        username = user.getUsername();
        salt = user.getSalt();
        status = user.getStatus();
        
        boolean doesPasswordMatch = authConfig.verifyPassword(passwordInput, password, salt);
        
        if(!doesPasswordMatch) {
            alertConf.showLoginErrorAlert(currentStage, "Password does not match.");
            return;
        }
        
        boolean isStatusActive = status.equals("active");
        
        if(!isStatusActive) {
            alertConf.showLoginErrorAlert(currentStage, "Account is not active yet.");
            return;
        }
        
        alertConf.showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Successfully Logged In", currentStage);
        
                
        switch(role) {
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
        Stage stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass.getResource(targetFXML));
        Parent root = loader.load();
        
        String userInput = usernameField.getText();
        
        User user = dbConf.getUserByUsername(userInput);
        
        String userRole = user.getRole();
        
        String teamMemberPageFXML = "/projectvantage/fxml/team_member/TeamMemberDashboardPage.fxml";
        String adminPageFXML = "/projectvantage/fxml/admin/AdminDashboardPage.fxml";
        
        switch(userRole){
            case "team member":
                TeamMemberMainPageController teamMemberController = loader.getController();
                teamMemberController.setUsername(userInput);
                pageConf.loadDashboardPage(stage, teamMemberPageFXML, userInput, teamMemberController.getBackgroundPane(), teamMemberController.getRootPane());
            break;
            case "admin":
                AdminPageController adminController = loader.getController();
                adminController.setUsername(userInput);
                pageConf.loadDashboardPage(stage, adminPageFXML, userInput, adminController.getBackgroundPane(), adminController.getRootPane());
            break;
        }
        
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
    private void passwordResetButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.switchScene(getClass(), event, "/projectvantage/fxml/misc/ForgotPasswordPage.fxml");
    }
    
}
