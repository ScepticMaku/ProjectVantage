/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.utility.AlertConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.Config;
import projectvantage.utility.dbConnect;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class EditUserPageController implements Initializable {
    
    private static EditUserPageController instance;
    
    dbConnect db = new dbConnect();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    Config config = new Config();
    DatabaseConfig dbConf = new DatabaseConfig();
    
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String status;
    private String role;
    private String username;

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
    private RadioButton teamMemberRadioButton;
    @FXML
    private RadioButton teamLeaderRadioButton;
    @FXML
    private RadioButton teamManagerRadioButton;
    @FXML
    private RadioButton projectManagerRadioButton;
    @FXML
    private RadioButton adminRadioButton;
    private PasswordField passwordField;
    @FXML
    private Button deactivateButton;
    @FXML
    private Button activateButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button submitUserButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }
    
    public static EditUserPageController getInstance() {
        return instance;
    }
    
    public void loadUserContents(String userInput) {
        
        User user = dbConf.getUserByUsername(userInput);
        
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        lastName = user.getLastName();
        emailAddress = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        username = user.getUsername();
        role = user.getRole();
        status = user.getStatus();
        
        firstNameField.setText(firstName);
        middleNameField.setText(middleName);
        lastNameField.setText(lastName);
        emailAddressField.setText(emailAddress);
        phoneNumberField.setText(phoneNumber);
        usernameLabel.setText(username);
        usernameField.setText(username);
        
        switch(role) {
            case "team member":
                teamMemberRadioButton.setSelected(true);
                break;
            case "admin":
                adminRadioButton.setSelected(true);
                break;
        }
        
        boolean isActive = status.equals("active");
        
        if(isActive) {
            activateButton.setVisible(false);
        }
        
        if(!isActive) {
            deactivateButton.setVisible(false);
        }
    }
    
    private boolean isUsernameDuplicated(String column, String value) throws SQLException {
        
        try(ResultSet result = db.getData("SELECT " + column + " FROM user WHERE NOT username = '" + username + "'")) {    
            while(result.next()) {
                if(value.equals(result.getString(column)))
                    return true;
            }
        }
        return false;
    }
    
    private boolean isEmailDuplicated(String column, String value) throws SQLException {
        
        try(ResultSet result = db.getData("SELECT " + column + " FROM user WHERE NOT email = '" + emailAddress + "'")) {    
            while(result.next()) {
                if(value.equals(result.getString(column)))
                    return true;
            }
        }
        return false;
    }
    
    private boolean verifyInput(Stage currentStage, String firstName, String lastName, String phoneNumber, String emailAddress, String user) throws Exception {
        if(firstName.isEmpty()) {
            alertConf.showEditEUserErrorAlert(currentStage, "First name field must not be empty.");
            return true;
        }
        
        if(lastName.isEmpty()) {
            alertConf.showEditEUserErrorAlert(currentStage, "Last name field must not be empty.");
            return true;
        }
        
        if(emailAddress.isEmpty()) {
            alertConf.showEditEUserErrorAlert(currentStage, "Email address field must not be empty.");
            return true;
        }
        
        if(phoneNumber.isEmpty()) {
            alertConf.showEditEUserErrorAlert(currentStage, "Phone number field must not be empty.");
            return true;
        }
        
        if(username.isEmpty()) {
            alertConf.showEditEUserErrorAlert(currentStage, "Username field must not be empty.");
            return true;
        }
        
        if(!config.isValidEmailFormat(emailAddress)) {
            alertConf.showEditEUserErrorAlert(currentStage, "Email format is invalid.");
            return true;
        }
        
        if(config.isValidPhoneNumber(phoneNumber)) {
            alertConf.showEditEUserErrorAlert(currentStage, "Phone number is invalid.");
            return true;
        }
        
        if(!config.isValidPhoneNumberFormat(phoneNumber)) {
            alertConf.showEditEUserErrorAlert(currentStage, "Phone number is invalid.");
            return true;
        }
        
        if(isUsernameDuplicated("username", user)) {
            alertConf.showEditEUserErrorAlert(currentStage, "Username already exists.");
            return true;
        }
        
        if(isEmailDuplicated("email", emailAddress)) {
            alertConf.showEditEUserErrorAlert(currentStage, "Username already exists.");
            return true;
        }
        return false;
    }
    
    private boolean updateUserStatus(String query) {
        if(db.updateData(query)) {
            System.out.println("User status updated!");
            return true;
        }
        return false;
    }
    
    private void refreshPage() throws Exception {
        String FXML = "/projectvantage/fxml/admin/EditUserPage.fxml";
        pageConf.loadWindow(FXML, "Edit user", rootPane);
        EditUserPageController.getInstance().loadUserContents(username);
    }
            
    @FXML
    private void backButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void teamMemberButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void teamLeaderButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void teamManagerMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void projectManagerMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void adminButtonMouseClickHandler(MouseEvent event) {
    }


    @FXML
    private void deactivateButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        String sql = "UPDATE user SET status_id = 1 WHERE username = '" + username + "'";
        
        if(!updateUserStatus(sql)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "User Deactivation Failed", "There was a problem deactivating the user.", currentStage);
            return;
        }
        
        alertConf.showAlert(Alert.AlertType.INFORMATION, "User Deactivation Successful", "User successfully deactivated!", currentStage);
        refreshPage();
    }

    @FXML
    private void activateButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        String sql = "UPDATE user SET status_id = 2 WHERE username = '" + username + "'";
        
        if(!updateUserStatus(sql)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "User Activation Failed", "There was a problem activating the user.", currentStage);
            return;
        }
        
        alertConf.showAlert(Alert.AlertType.INFORMATION, "User Activation", "User successfully activated!", currentStage);
        refreshPage();
    }

    @FXML
    private void submitUserButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        
        String fName = firstNameField.getText();
        String mName = middleNameField.getText();
        String lName = lastNameField.getText();
        String pNumber = phoneNumberField.getText();
        String eAddress = emailAddressField.getText();
        String uName = usernameField.getText();
        
        String userRole;
        
        if(teamMemberRadioButton.isSelected()) {
            userRole = "team member";
        } else if(teamLeaderRadioButton.isSelected()) {
            userRole = "team leader";
        } else if(teamManagerRadioButton.isSelected()) {
            userRole = "team manager";
        } else if(projectManagerRadioButton.isSelected()) {
            userRole = "project manager";
        } else if(adminRadioButton.isSelected()) {
            userRole = "admin";
        } else {
            alertConf.showEditEUserErrorAlert(currentStage, "You must select a type of user.");
            return;
        }
        
        String sql = "UPDATE user SET first_name = ?, middle_name = ?, last_name = ?, phone_number = ?, email = ?, username = ? WHERE username = ?";
        
        if(!verifyInput(currentStage, fName, lName, pNumber, eAddress, uName)) {
            if(db.updateData(sql, fName, mName, lName, pNumber, eAddress, uName, username)) {
                System.out.println("User updated successfully!");
                alertConf.showAlert(Alert.AlertType.INFORMATION, "User Update Successful", "User Updated Succesfully!", currentStage);
                currentStage.close();
            }
        }
    }
}
