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
import javafx.scene.image.ImageView;
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
    StringBuilder storedPassword = new StringBuilder();
    
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
//        password = user.getPassword();
        username = user.getUsername();
        salt = user.getSalt();
        status = user.getStatus();
        
        boolean doesPasswordMatch = authConfig.verifyPassword(storedPassword.toString(), password, salt);
        
        if(!doesPasswordMatch) {
            alertConf.showLoginErrorAlert(currentStage, "Password does not match.");
            return; 
        }
        
        boolean isStatusActive = status.equals("active");
        
        if(!isStatusActive) {
            alertConf.showLoginErrorAlert(currentStage, "Account is not active yet.");
            return;
        }
        
        alertConf.showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Successfully logged in", currentStage);
        
                
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
    
    private String maskText(int length) {
        StringBuilder maskedText = new StringBuilder();
        
        for(int i = 0; i < length; i++) {
            maskedText.append('\u2022');
        }
        return maskedText.toString();
    }
    
    private void maskLastChar(TextField field) {
        field.setText(maskText(passwordField.getText().length()));
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
    }

    @FXML
    private void rootPaneKeyPressedHandler(KeyEvent event) throws Exception {
        if(isTabPressed(event)) {
            usernameField.setFocusTraversable(true);
        }
        
        if(isEnterPressed(event))
            loginUser(event);
    }

    @FXML
    private void passwordResetButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.switchScene(getClass(), event, "/projectvantage/fxml/misc/ForgotPasswordPage.fxml");
    }

    @FXML
    private void hidePasswordButtonMouseClickHandler(MouseEvent event) {
        hidePasswordButton.setVisible(false);
        showPasswordButton.setVisible(true);
        
        passwordField.setText(storedPassword.toString());
        passwordField.positionCaret(passwordField.getText().length());
    }

    @FXML
    private void showPaswordButtonMouseClickHandler(MouseEvent event) {
        showPasswordButton.setVisible(false);
        hidePasswordButton.setVisible(true);
        
        passwordField.setText(maskText(storedPassword.toString().length()));
        passwordField.positionCaret(passwordField.getText().length());
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
    private void passwordFieldKeyPressHandler(KeyEvent event) {
        boolean isPasswordFieldEmpty = passwordField.getText().isEmpty();
        
        if(!isPasswordFieldEmpty) {
            hidePasswordButton.setVisible(true);
            return;
        }   
        
        if(isPasswordFieldEmpty) {
            hidePasswordButton.setVisible(false);
            storedPassword.delete(0, storedPassword.toString().length());
        }
    }

    @FXML
    private void passwordFieldKeyTypedHandler(KeyEvent event) {
        storedPassword.append(event.getCharacter());
        
        passwordField.setText(maskText(passwordField.getText().length()));
        passwordField.positionCaret(passwordField.getText().length());
    }
}
