/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.authentication;

import projectvantage.utility.SessionConfig;
import projectvantage.utility.Config;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.PageConfig;
import projectvantage.controllers.team_member.TeamMemberMainPageController;
import projectvantage.controllers.project_manager.ProjectManagerPageController;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.utility.AuthenticationConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.controllers.misc.AuthenticationController;
import projectvantage.utility.AlertConfig;
import projectvantage.models.User;
import projectvantage.utility.LogConfig;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import projectvantage.controllers.team_manager.TeamManagerPageController;

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
    LogConfig logConf = new LogConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    dbConnect db = new dbConnect();
    AlertConfig alertConf = new AlertConfig();
    AuthenticationController authController = new AuthenticationController();
    
    private static LoginController instance;
    
    private int userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String role;
    private String password;
    private String username;
    private String salt;
    private String status;
    private String email;
    private String secretKey;
    
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
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
    @FXML
    private ImageView hidePasswordButton;
    @FXML
    private ImageView showPasswordButton;
    @FXML
    private TextField revealedPasswordField;

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
    
    public void loginUser(Stage currentStage, String userInput) throws Exception {
        
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
        
        userId = user.getId();
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        lastName = user.getLastName();
        phoneNumber = user.getPhoneNumber();
        role = user.getRole();
        password = user.getPassword();
        username = user.getUsername();
        salt = user.getSalt();
        status = user.getStatus();
        secretKey = user.getSecretKey();
        email = user.getEmail();
        
        boolean doesPasswordMatch = authConfig.verifyPassword(passwordInput, password, salt);
        
        if(!doesPasswordMatch) {
            alertConf.showLoginErrorAlert(currentStage, "Password does not match.");
            logConf.loginLog(false, user.getId(), "Password does not match.");
            return; 
        }
        
        boolean isStatusActive = status.equals("active");
        
        if(!isStatusActive) {
            alertConf.showLoginErrorAlert(currentStage, "Account is not active yet.");
            logConf.loginLog(false, user.getId(), "Account is not active yet.");
            return;
        }
        
        alertConf.showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Successfully logged in.", currentStage);
        
        switch(role) {
            case "admin":
                switchScene(userInput, getClass(), "/projectvantage/fxml/admin/AdminPage.fxml");
                break;
            case "standard":
                switchScene(userInput, getClass(), "/projectvantage/fxml/team_member/TeamMemberMainPage.fxml");
                break;
            case "project manager":
                switchScene(userInput, getClass(), "/projectvantage/fxml/project_manager/ProjectManagerPage.fxml");
                break;
            case "team manager":
                switchScene(userInput, getClass(), "/projectvantage/fxml/team_manager/TeamManagerPage.fxml");
                break;
            default:
                alertConf.showLoginErrorAlert(currentStage, "Role not found.");
                logConf.loginLog(false, user.getId(), "Role not found.");
        }
        
        logConf.loginLog(true, user.getId(), "Successfully logged in");
        alertConf.showAlert(Alert.AlertType.INFORMATION, "Login Alert", "Welcome " + username + "!", currentStage);
    }
    
    public void switchScene(String userInput, Class getClass, String targetFXML) throws Exception {
        SessionConfig sessionConf = new SessionConfig();
        Stage stage = (Stage)rootPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass.getResource(targetFXML));
        Parent root = loader.load();
        
        authConfig.rememberUser(userInput);
        sessionConf.setSession(userId, firstName, middleName, lastName, email, phoneNumber, username, role);
        
        if(secretKey != null) {
            sessionConf.setSecretKey(secretKey);
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
        Stage stage = (Stage)rootPane.getScene().getWindow();
        loginUser(stage, usernameField.getText());
    }
    

    @FXML
    private void usernameFieldOnKeyPressedHandler(KeyEvent event) throws Exception {
        if(isTabPressed(event)) {
            passwordField.setFocusTraversable(true);
            passwordField.requestFocus();
        }
    }

    @FXML
    private void rootPaneKeyPressedHandler(KeyEvent event) throws Exception {
        Stage stage = (Stage)rootPane.getScene().getWindow();
        
        if(isTabPressed(event)) {
            usernameField.setFocusTraversable(true);
            usernameField.requestFocus();
        }
        
        if(isEnterPressed(event))
            loginUser(stage, usernameField.getText());
    }

    @FXML
    private void passwordResetButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/misc/ForgotPasswordPage.fxml", "Password Reset",rootPane);
    }

    @FXML
    private void hidePasswordButtonMouseClickHandler(MouseEvent event) {
        hidePasswordButton.setVisible(false);
        showPasswordButton.setVisible(true);
        
        revealedPasswordField.setOpacity(1.0);
        revealedPasswordField.toFront();
        passwordField.setOpacity(0);
    }

    @FXML
    private void showPaswordButtonMouseClickHandler(MouseEvent event) {
        showPasswordButton.setVisible(false);
        hidePasswordButton.setVisible(true);
        
        passwordField.setOpacity(1.0);
        passwordField.toFront();
        revealedPasswordField.setOpacity(0);
    }

    @FXML
    private void hidePasswordButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(hidePasswordButton);
    }

    @FXML
    private void hidePasswordButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(hidePasswordButton);
    }

    @FXML
    private void hidePasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(hidePasswordButton);
    }

    @FXML
    private void showPasswordButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(showPasswordButton);
    }

    @FXML
    private void showPasswordButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(showPasswordButton);
    }

    @FXML
    private void showPasswordButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(showPasswordButton);
    }

    @FXML
    private void showPasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(showPasswordButton);
    }

    @FXML
    private void hidePasswordButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(hidePasswordButton);
    }

    @FXML
    private void passwordFieldKeyTypedHandler(KeyEvent event) {
        String passField = passwordField.getText();
        revealedPasswordField.textProperty().bindBidirectional(passwordField.textProperty());
        
        hidePasswordButton.setVisible(false);
        
        if(!passField.isEmpty()) 
            hidePasswordButton.setVisible(true);
    }

}
