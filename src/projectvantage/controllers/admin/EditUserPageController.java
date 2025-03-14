/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.controllers.authentication.LoginController;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.utility.PageConfig;
import projectvantage.utility.Config;
import projectvantage.utility.dbConnect;

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
    PageConfig pageConf = new PageConfig();
    Config config = new Config();
    
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
    
    public void loadUserContents(String...info) {
        firstName = info[0];
        middleName = info[1];
        lastName = info[2];
        emailAddress = info[3];
        phoneNumber = info[4];
        username = info[5];
        role = info[6];
        status = info[7];
        
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
        
        if(status.equals("active")) {
            activateButton.setVisible(false);
        }
        
        if(status.equals("inactive")) {
            deactivateButton.setVisible(false);
        }
    }
    
    private boolean isUsernameDuplicated(String column, String value) throws SQLException {
        dbConnect db = new dbConnect();
        
        try(ResultSet result = db.getData("SELECT " + column + " FROM user WHERE NOT username = '" + username + "'")) {    
            while(result.next()) {
                if(value.equals(result.getString(column)))
                    return true;
            }
        }
        return false;
    }
    
    private boolean isEmailDuplicated(String column, String value) throws SQLException {
        dbConnect db = new dbConnect();
        
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
        
        if(isUsernameDuplicated("username", user)) {
            config.showErrorMessage("Username already exists", "Username Error", currentStage);
            return true;
        }
        
        if(isEmailDuplicated("email", emailAddress)) {
            config.showErrorMessage("Email already exist.", "Email Error", currentStage);
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
    
    private void returnToPreviousPage(String user) {
        AdminPageController adminController = AdminPageController.getInstance();
        String fxmlLocation = "/projectvantage/fxml/admin/AdminUserPage.fxml";
        pageConf.loadUserPage(fxmlLocation, user ,adminController.getBackgroundPane(), adminController.getRootPane());
        adminController.getTitlebarLabel().setText("User");
    }
    
    private void refreshPage() {
        AdminPageController adminController = AdminPageController.getInstance();
        String FXML = "/projectvantage/fxml/admin/EditUserPage.fxml";
        pageConf.loadEditUserPage(FXML, username, adminController.getBackgroundPane(), adminController.getRootPane());
        adminController.getTitlebarLabel().setText("Edit User");
    }
            
    @FXML
    private void backButtonMouseClickHandler(MouseEvent event) {
        returnToPreviousPage(username);
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
    private void deactivateButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        String sql = "UPDATE user SET status = 'inactive' WHERE username = '" + username + "'";
        
        if(!updateUserStatus(sql)) {
            config.showErrorMessage("User deactivation failed", "User Dectivation Error", currentStage);
            return;
        }
        
        config.showAlert(Alert.AlertType.INFORMATION, "User Deactivation", "User successfully deactivated!", currentStage);
        refreshPage();
    }

    @FXML
    private void activateButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        String sql = "UPDATE user SET status = 'active' WHERE username = '" + username + "'";
        
        if(!updateUserStatus(sql)) {
            config.showErrorMessage("User activation failed", "User Activation Error", currentStage);
            return;
        }
        
        config.showAlert(Alert.AlertType.INFORMATION, "User Activation", "User successfully activated!", currentStage);
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
            config.showErrorMessage("You must select a type of user", "User type error", currentStage);
            return;
        }
        
        String sql = "UPDATE user SET first_name = ?, middle_name = ?, last_name = ?, phone_number = ?, email = ?, username = ? WHERE username = ?";
        
        if(!verifyInput(currentStage, fName, lName, pNumber, eAddress, uName)) {
            if(db.updateData(sql, fName, mName, lName, pNumber, eAddress, uName, username)) {
                System.out.println("User updated successfully!");
                config.showAlert(Alert.AlertType.INFORMATION, "User Update", "User Updated Succesfully!", currentStage);
                returnToPreviousPage(uName);
            }
        }
    }
}
