/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.authentication;

import projectvantage.utility.Config;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class RegisterController implements Initializable {

    @FXML
    private Pane registerPane;
    @FXML
    private Label loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField middleNameField;
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
    
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    Config config = new Config();

    @FXML
    private void loginButtonMouseClickHandler(MouseEvent event) {
        AuthenticationController authControl = AuthenticationController.getInstance();
        
        if(authControl != null) {
            Pane loginPane = authControl.getLoginPane();
            Pane otherPane = authControl.getOtherPane();
            Pane title = authControl.getTitlePane();
            
            loginPane.setVisible(true);
            otherPane.setVisible(false);
            title.setLayoutY(157);
        }
    }

    @FXML
    private void registerButtonMouseClickHandler(MouseEvent event) throws SQLException {
        String firstName = firstNameField.getText();
        String middleName = middleNameField.getText();
        String lastName = lastNameField.getText();
        String emailAddress = emailAddressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String passwordConfirm = passwordConfirmField.getText();
        
        if(firstName.isEmpty() || lastName.isEmpty() || emailAddress.isEmpty() || phoneNumber.isEmpty() || 
                username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            config.showErrorMessage("Fields must not be empty", "Field Error");
            return;
        }
        
        if(config.checkPhoneNumber(phoneNumber)) {
            config.showErrorMessage("Phone number must only be numbers.", "Phone number Error");
            return;
        }
        
        if(password.length() < MINIMUM_PASSWORD_LENGTH) {
            config.showErrorMessage("Password must be at least 8 characters.", "Password Error");
            return;
        }
        
        if(config.isDuplicated("username", username)) {
            config.showErrorMessage("Username already exists", "Username Error");
            return;
        }
        
        if(config.isDuplicated("email", emailAddress)) {
            config.showErrorMessage("Email already exist.", "Email Error");
            return;
        }
    }
    
}
