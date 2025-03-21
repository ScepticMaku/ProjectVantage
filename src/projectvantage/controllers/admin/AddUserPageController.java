/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.controllers.authentication.RegisterController;
import projectvantage.utility.dbConnect;
import projectvantage.utility.Config;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.AuthenticationConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class AddUserPageController implements Initializable {
    
    RegisterController registerController = new RegisterController();
    dbConnect connect = new dbConnect();
    Config config = new Config();
    AuthenticationConfig authConf = new AuthenticationConfig();
    AlertConfig alertConf = new AlertConfig();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button backButton;
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
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private RadioButton teamMemberRadioButton;
    @FXML
    private RadioButton teamLeaderRadioButton;
    @FXML
    private RadioButton teamManagerRadioButton;
    @FXML
    private RadioButton projectManagerRadioButton;
    @FXML
    private Button addUserButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private void insertUser(Stage currentStage, String query, String firstName, String middleName, String lastName, String emailAddress, String phoneNumber, String username, String salt, String password, String role) {
        if(connect.insertData(query, firstName, middleName, lastName, emailAddress, phoneNumber, username, salt, password, role)) {
            System.out.println("User added to database!");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "User successfully registered!", "Register Successful", currentStage);
        }
    }
    
    private void returnToPreviousPage() {
        String FXML = "/projectvantage/fxml/admin/UserManagementPage.fxml";
        
        AdminPageController admin = AdminPageController.getInstance();
        admin.loadPage(FXML, "Users");
    }

    @FXML
    private void backButtonMouseClickHandler(MouseEvent event) {
        returnToPreviousPage();
    }

    @FXML
    private void addUserButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        
        String firstName = firstNameField.getText();
        String middleName = middleNameField.getText();
        String lastName = lastNameField.getText();
        String emailAddress = emailAddressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String passwordConfirm = confirmPasswordField.getText();
        
        String role;
        
        if(teamMemberRadioButton.isSelected()) {
            role = "team member";
        } else if(teamLeaderRadioButton.isSelected()) {
            role = "team leader";
        } else if(teamManagerRadioButton.isSelected()) {
            role = "team manager";
        } else if(projectManagerRadioButton.isSelected()) {
            role = "project manager";
        } else {
            alertConf.showRegisterErrorAlert(currentStage, "You must select a type of user.");
            return;
        }
        
        String query = "INSERT INTO user (first_name, middle_name, last_name, email, phone_number, username, salt, password, role, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'inactive')";
        
        if(registerController.verifyUser(currentStage, firstName, lastName, emailAddress, phoneNumber, username, password, passwordConfirm))
            return;
        
        String salt = authConf.generateSalt();
        String hashedPassword = authConf.hashPassword(password, salt);

        insertUser(currentStage, query, firstName, middleName, lastName, emailAddress, phoneNumber, username, salt, hashedPassword, role);
        returnToPreviousPage();
        
    }

    @FXML
    private void teamMemberButtonMouseClickHandler(MouseEvent event) {
            teamLeaderRadioButton.setSelected(false);
            teamManagerRadioButton.setSelected(false);
            projectManagerRadioButton.setSelected(false);
    }

    @FXML
    private void teamLeaderButtonMouseClickHandler(MouseEvent event) {
            teamMemberRadioButton.setSelected(false);
            teamManagerRadioButton.setSelected(false);
            projectManagerRadioButton.setSelected(false);
    }

    @FXML
    private void teamManagerMouseClickHandler(MouseEvent event) {
            teamLeaderRadioButton.setSelected(false);
            teamMemberRadioButton.setSelected(false);
            projectManagerRadioButton.setSelected(false);
        
    }

    @FXML
    private void projectManagerMouseClickHandler(MouseEvent event) {
            teamLeaderRadioButton.setSelected(false);
            teamManagerRadioButton.setSelected(false);
            teamMemberRadioButton.setSelected(false);
    }
}
