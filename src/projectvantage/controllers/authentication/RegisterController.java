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
    private ImageView closeButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField middleNameField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private void switchToLogin(MouseEvent event) throws Exception {
        /*AuthenticationController authControl = AuthenticationController.getInstance();
        
        if(authControl != null) {
            Pane loginPane = authControl.getLoginPane();
            Pane otherPane = authControl.getOtherPane();
            Pane title = authControl.getTitlePane();
            
            otherPane.setVisible(false);
            firstNameField.setText("");
            lastNameField.setText("");
            emailAddressField.setText("");
            phoneNumberField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            passwordConfirmField.setText("");
            
            title.setLayoutY(150);
            title.setLayoutX(500);
            
            loginPane.setVisible(true);
        }*/
        String FXML = "/projectvantage/fxml/authentication/Login.fxml";
        pageConf.switchScene(getClass(), event, FXML);
    }
    
    public boolean verifyUser(Stage currentStage, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password, String passwordConfirm) throws Exception {
        if(firstName.isEmpty()) {
            config.showErrorMessage("First name must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(lastName.isEmpty()) {
            config.showErrorMessage("Last name must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(emailAddress.isEmpty()) {
            config.showErrorMessage("Email address must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(phoneNumber.isEmpty()) {
            config.showErrorMessage("Phone number must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(username.isEmpty()) {
            config.showErrorMessage("username must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(password.isEmpty()) {
            config.showErrorMessage("password must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(passwordConfirm.isEmpty()) {
            config.showErrorMessage("confirm password must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(!config.isValidEmailFormat(emailAddress)) {
            config.showErrorMessage("Email format is invalid", "Email Error", currentStage);
            return true;
        }
        
        if(config.isValidPhoneNumber(phoneNumber)) {
            config.showErrorMessage("Phone number must only be numbers.", "Phone Number Error", currentStage);
            return true;
        }
        
        if(config.isValidPhoneNumberFormat(phoneNumber)) {
            config.showErrorMessage("Phone number format is invalid, must start with 9", "Phone Number Error", currentStage);
            return true;
        }
        
        if(password.length() < MINIMUM_PASSWORD_LENGTH) {
            config.showErrorMessage("Password must be at least 8 characters.", "Password Error", currentStage);
            return true;
        }
        
        if(!password.equals(passwordConfirm)) {
            config.showErrorMessage("Password doesn't match.", "Password Error", currentStage);
            return true;
        }
        
        if(config.isDuplicated("username", username)) {
            config.showErrorMessage("Username already exists", "Username Error", currentStage);
            return true;
        }
        
        if(config.isDuplicated("email", emailAddress)) {
            config.showErrorMessage("Email already exist.", "Email Error", currentStage);
            return true;
        }
        return false;
    }

    @FXML
    private void loginButtonMouseClickHandler(MouseEvent event) throws Exception {
        switchToLogin(event);
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
        
        String query = "INSERT INTO user (first_name, middle_name, last_name, email, phone_number, username, password, role, status) "
                + "VALUES ( ?, ?, ?, ?, ?, ? ,? , 'team member' , 'inactive')";
        
        if(!verifyUser(currentStage, firstName, lastName, emailAddress, phoneNumber, username, password, passwordConfirm)) {
           if(connect.insertData(query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password)) {
                System.out.println("User added to database!");
                config.showAlert(Alert.AlertType.INFORMATION, "User successfully registered!", "Register Completed!", currentStage);
                switchToLogin(event);
            } 
        }  
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
    private void closeButtonMouseReleaseHandler(MouseEvent event) {
        exitButtonBG.setFill(Color.web("#d71515"));
    }

    @FXML
    private void closeButtonMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(exitButtonBG);
    }

    @FXML
    private void closeButtonMouseEnterHandler(MouseEvent event) {
        elementConf.fadeIn(exitButtonBG);
    }

    @FXML
    private void closeButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        config.showAlert(Alert.AlertType.CONFIRMATION, "Exit Confirmtaion.", "Do you want to exit?", currentStage);
    }

    @FXML
    private void closeButtonMousePressHandler(MouseEvent event) {
        exitButtonBG.setFill(Color.web("#971111"));
    }
    
}
