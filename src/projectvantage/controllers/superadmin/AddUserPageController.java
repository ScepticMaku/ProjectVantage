/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.superadmin;

import projectvantage.controllers.authentication.RegisterController;
import projectvantage.utility.dbConnect;
import projectvantage.utility.Config;

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
    private RadioButton adminRadioButton;
    @FXML
    private Button addUserButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    RegisterController register = new RegisterController();
    dbConnect connect = new dbConnect();
    Config config = new Config();
    
    private void insertUser(Stage currentStage, String query, String firstName, String middleName, String lastName, String emailAddress, String phoneNumber, String username, String password, String role) {
        if(connect.insertData(query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password, role)) {
            System.out.println("User added to database!");
            config.showAlert(Alert.AlertType.INFORMATION, "User successfully registered!", "Register Completed!", currentStage);
        }
    }

    @FXML
    private void backButtonMouseClickHandler(MouseEvent event) {
        String FXML = "/projectvantage/fxml/superadmin/UserManagementPage.fxml";
        SuperAdminPageController admin = SuperAdminPageController.getInstance();
        admin.loadPage(FXML);
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
        
        String role = "team member";
        
        if(teamMemberRadioButton.isSelected()) {
            role = "team member";
        } else if(teamLeaderRadioButton.isSelected()) {
            role = "team leader";
        } else if(teamManagerRadioButton.isSelected()) {
            role = "team manager";
        } else if(projectManagerRadioButton.isSelected()) {
            role = "project manager";
        } else if(adminRadioButton.isSelected()) {
            role = "admin";
        } else {
            config.showErrorMessage("You must select a type of user", "User type error", currentStage);
            return;
        }
        
        String query = "INSERT INTO user (first_name, middle_name, last_name, email, phone_number, username, password, role, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'inactive')";
        
        if(!register.verifyUser(currentStage, query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password, passwordConfirm)) {
//            activateUser(currentStage, query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password, role, status);
            insertUser(currentStage, query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password, role);
        }
    }

    @FXML
    private void teamMemberButtonMouseClickHandler(MouseEvent event) {
        if(teamMemberRadioButton.isSelected()) {
            teamLeaderRadioButton.setSelected(false);
            teamManagerRadioButton.setSelected(false);
            projectManagerRadioButton.setSelected(false);
            adminRadioButton.setSelected(false);
        }
    }

    @FXML
    private void teamLeaderButtonMouseClickHandler(MouseEvent event) {
        if(teamLeaderRadioButton.isSelected()) {
            teamMemberRadioButton.setSelected(false);
            teamManagerRadioButton.setSelected(false);
            projectManagerRadioButton.setSelected(false);
            adminRadioButton.setSelected(false);
        }
    }

    @FXML
    private void teamManagerMouseClickHandler(MouseEvent event) {
        if(teamManagerRadioButton.isSelected()) {
            teamLeaderRadioButton.setSelected(false);
            teamMemberRadioButton.setSelected(false);
            projectManagerRadioButton.setSelected(false);
            adminRadioButton.setSelected(false);
        }
    }

    @FXML
    private void projectManagerMouseClickHandler(MouseEvent event) {
        if(projectManagerRadioButton.isSelected()) {
            teamLeaderRadioButton.setSelected(false);
            teamManagerRadioButton.setSelected(false);
            teamMemberRadioButton.setSelected(false);
            adminRadioButton.setSelected(false);
        }
    }

    @FXML
    private void adminButtonMouseClickHandler(MouseEvent event) {
        if(adminRadioButton.isSelected()) {
            teamLeaderRadioButton.setSelected(false);
            teamManagerRadioButton.setSelected(false);
            projectManagerRadioButton.setSelected(false);
            teamMemberRadioButton.setSelected(false);
        }
    }
}
