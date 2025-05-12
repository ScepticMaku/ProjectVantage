/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.authentication;

import projectvantage.utility.Config;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.AuthenticationConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.LogConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class RegisterController implements Initializable {
    
    Config config = new Config();
    ElementConfig elementConf = new ElementConfig();
    PageConfig pageConf = new PageConfig();
    dbConnect connect = new dbConnect();
    AuthenticationConfig authConf = new AuthenticationConfig();
    GoogleAuthenticationController googleAuth = GoogleAuthenticationController.getInstance();
    AlertConfig alertConf = new AlertConfig();
    LogConfig logConf = new LogConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    @FXML
    private Pane registerPane;
    @FXML
    private Label loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailAddressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordConfirmField;
    @FXML
    private Pane titlePane;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Rectangle exitButtonBG;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField middleNameField;
    @FXML
    private Label registerLabel;
    @FXML
    private ImageView hidePasswordButton;
    @FXML
    private ImageView showPasswordButton;
    @FXML
    private TextField revealedPasswordField;
    @FXML
    private TextField revealedConfirmPasswordField;
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
    }
    
    public boolean verifyUser(Stage currentStage, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password, String passwordConfirm) throws Exception {
        if(firstName.isEmpty()) {
            alertConf.showRegisterErrorAlert(currentStage, "First name field must not be empty.");
            return true;
        }
        
        if(lastName.isEmpty()) {
            alertConf.showRegisterErrorAlert(currentStage, "Last name must field not be empty.");
            return true;
        }
        
        if(emailAddress.isEmpty()) {
            alertConf.showRegisterErrorAlert(currentStage, "Email address field must not be empty.");
            return true;
        }
        
        if(phoneNumber.isEmpty()) {
            alertConf.showRegisterErrorAlert(currentStage, "Phone number field must not be empty.");
            return true;
        }
        
        if(username.isEmpty()) {
            alertConf.showRegisterErrorAlert(currentStage, "Username field must not be empty.");
            return true;
        }
        
        if(password.isEmpty()) {
            alertConf.showRegisterErrorAlert(currentStage, "Password field must not be empty.");
            return true;
        }
        
        if(passwordConfirm.isEmpty()) {
            alertConf.showRegisterErrorAlert(currentStage, "Confim password field must not be empty.");
            return true;
        }
        
        if(!config.isValidEmailFormat(emailAddress)) {
            alertConf.showRegisterErrorAlert(currentStage, "Email format is invalid.");
            return true;
        }
        
        if(config.isValidPhoneNumber(phoneNumber)) {
            alertConf.showRegisterErrorAlert(currentStage, "Phone number is invalid.");
            return true;
        }
        
        if(!config.isValidPhoneNumberFormat(phoneNumber)) {
            alertConf.showRegisterErrorAlert(currentStage, "Phone number is invalid.");
            return true;
        }
        
        if(password.length() < MINIMUM_PASSWORD_LENGTH) {
            alertConf.showRegisterErrorAlert(currentStage, "Password must be at least 8 characters.");
            return true;
        }
        
        if(!password.equals(passwordConfirm)) {
            alertConf.showRegisterErrorAlert(currentStage, "Password does not match.");
            return true;
        }
        
        if(config.isDuplicated("username", username)) {
            alertConf.showRegisterErrorAlert(currentStage, "Username already exists.");
            return true;
        }
        
        if(config.isDuplicated("email", emailAddress)) {
            alertConf.showRegisterErrorAlert(currentStage, "Email already exists.");
            return true;
        }
        return false;
    }

    @FXML
    private void loginButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        String FXML = "/projectvantage/fxml/authentication/Login.fxml";
        
        pageConf.switchScene(getClass(), event, FXML);
        currentStage.setTitle("Login");
    }

    @FXML
    private void registerButtonMouseClickHandler(MouseEvent event) throws SQLException, Exception {
        Stage currentStage = (Stage)registerPane.getScene().getWindow();
        
        String firstName = firstNameField.getText();
        String middleName = middleNameField.getText();
        String lastName = lastNameField.getText();
        String emailAddress = emailAddressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String passwordConfirm = passwordConfirmField.getText();
        
        String query = "INSERT INTO user (first_name, middle_name, last_name, email, phone_number, username, salt, password) "
                + "VALUES ( ?, ?, ?, ?, ?, ? ,?, ?)";
        
        if(verifyUser(currentStage, firstName, lastName, emailAddress, phoneNumber, username, password, passwordConfirm))
            return;
        
        String salt = authConf.generateSalt();
        String hashedPassword = authConf.hashPassword(passwordConfirm, salt);
        
        if(connect.executeQuery(query, firstName, middleName, lastName, emailAddress, phoneNumber, username, salt, hashedPassword)) {
             System.out.println("User added to database!");
             alertConf.showAlert(Alert.AlertType.INFORMATION, "User successfully registered!", "Register Completed!", currentStage);
         }
        
        User user = databaseConf.getUserByUsername(username);
        
        logConf.registerLog(user.getId());
        
        String loginFXML = "/projectvantage/fxml/authentication/Login.fxml";
        
        pageConf.switchScene(getClass(), event, loginFXML);
        currentStage.setTitle("Login");
    }

    @FXML
    private void loginButtonMouseExitHandler(MouseEvent event) {
        loginButton.setStyle("-fx-text-fill: #0593ff");
    }

    @FXML
    private void loginButtonMouseEnterHandler(MouseEvent event) {
        loginButton.setStyle("-fx-text-fill: #0676c6");
    }

    @FXML
    private void loginButtonMousePressHandler(MouseEvent event) {
        loginButton.setStyle("-fx-text-fill: #01528d");
    }

    @FXML
    private void hidePasswordButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(hidePasswordButton);
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
    private void hidePasswordButtonMouseClickHandler(MouseEvent event) {
        hidePasswordButton.setVisible(false);
        showPasswordButton.setVisible(true);
        
        revealedPasswordField.setOpacity(1.0);
        revealedPasswordField.toFront();
        passwordField.setOpacity(0);
    }

    @FXML
    private void hidePasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(hidePasswordButton);
    }

    @FXML
    private void showPasswordButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(hidePasswordButton);
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
    private void showPaswordButtonMouseClickHandler(MouseEvent event) {
        showPasswordButton.setVisible(false);
        hidePasswordButton.setVisible(true);
        
        passwordField.setOpacity(1.0);
        passwordField.toFront();
        revealedPasswordField.setOpacity(0);


    }

    @FXML
    private void showPasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(showPasswordButton);
    }

    @FXML
    private void passwordFieldKeyTypedHandler(KeyEvent event) {
        String passField = passwordField.getText();
        revealedPasswordField.textProperty().bindBidirectional(passwordField.textProperty());
        
        hidePasswordButton.setVisible(false);
        
        if(!passField.isEmpty()) 
            hidePasswordButton.setVisible(true);
    }

    @FXML
    private void passwordConfirmFieldKeyTypedHandler(KeyEvent event) {
        String passConfirmField = passwordConfirmField.getText();
        revealedConfirmPasswordField.textProperty().bindBidirectional(passwordConfirmField.textProperty());
        
        hideConfirmPasswordButton.setVisible(false);
        
        if(!passConfirmField.isEmpty()) 
            hideConfirmPasswordButton.setVisible(true);
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
        passwordConfirmField.setOpacity(0);
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
        elementConf.hoverIcon(showConfirmPasswordButton);
    }

    @FXML
    private void showConfirmPasswordButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(showConfirmPasswordButton);
    }

    @FXML
    private void showConfirmPaswordButtonMouseClickHandler(MouseEvent event) {
        showConfirmPasswordButton.setVisible(false);
        hideConfirmPasswordButton.setVisible(true);
        
        passwordConfirmField.setOpacity(1.0);
        passwordConfirmField.toFront();
        revealedConfirmPasswordField.setOpacity(0);
    }

    @FXML
    private void showConfirmPasswordButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(showConfirmPasswordButton);
    }
}
