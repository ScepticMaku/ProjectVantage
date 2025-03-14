/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.PageConfig;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.controllers.team_member.TeamMemberMainPageController;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class EditProfilePageController implements Initializable {
    
    private static EditProfilePageController instance;
    
    dbConnect db = new dbConnect();
    PageConfig pageConf = new PageConfig();
    Config config = new Config();
    
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String username;
    private String role;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label usernamePlaceholder;
    @FXML
    private Button backButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField emailAddressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private Button submitButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label rolePlaceholder;
    @FXML
    private PasswordField confirmPasswordField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }
    
    public static EditProfilePageController getInstance() {
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
        
        firstNameField.setText(firstName);
        middleNameField.setText(middleName);
        lastNameField.setText(lastName);
        emailAddressField.setText(emailAddress);
        phoneNumberField.setText(phoneNumber);
        usernamePlaceholder.setText(username);
        rolePlaceholder.setText(role);
    }
    
    private void returnToPreviousPage() {
        String fxmlLocation = "/projectvantage/fxml/misc/ProfilePage.fxml";
        TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
        AdminPageController adminController = AdminPageController.getInstance();
        
        switch(role) {
            case "team member":
                pageConf.loadProfilePage(fxmlLocation, username, teamMemberController.getBackgroundPane(), teamMemberController.getRootPane());
                teamMemberController.getTitlebarLabel().setText("Profile");
                break;
            case "admin":
                pageConf.loadProfilePage(fxmlLocation, username, adminController.getBackgroundPane(), adminController.getRootPane());
                adminController.getTitlebarLabel().setText("Profile");
                break;
        }
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
    
    private String getPassword(String user) {
        try(ResultSet result = db.getData("SELECT password FROM user  WHERE username = '" + user + "'")) {
            if(result.next()) {
                return result.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    private boolean verifyInput(Stage currentStage, String fName, String lName, String eAddress, String pNumber, String password, String confirmPassword) throws Exception {
        if(fName.isEmpty()) {
            config.showErrorMessage("First name must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(lName.isEmpty()) {
            config.showErrorMessage("Last name must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(eAddress.isEmpty()) {
            config.showErrorMessage("Email address must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(pNumber.isEmpty()) {
            config.showErrorMessage("Phone number must not be empty", "Field Error", currentStage);
            return true;
        }
        
        if(!config.isValidEmailFormat(eAddress)) {
            config.showErrorMessage("Email format is invalid", "Email Error", currentStage);
            return true;
        }
        
        if(config.isValidPhoneNumber(pNumber)) {
            config.showErrorMessage("Phone number must only be numbers", "Phone Number Error", currentStage);
            return true;
        }
        
        if(config.isValidPhoneNumberFormat(pNumber)) {
            config.showErrorMessage("Phone number format is invalid, must start with 9", "Phone Number Error", currentStage);
            return true;
        }
        
        if(isEmailDuplicated("email", eAddress)) {
            config.showErrorMessage("Email already exist", "Email Error", currentStage);
            return true;
        }
        
        if(password.isEmpty()) {
            config.showErrorMessage("You must enter your password to submit changes", "Edit Error", currentStage);
            return true;
        }
        
        if(!getPassword(username).equals(password)) {
            config.showErrorMessage("Incorrect password", "Password Error", currentStage);
            return true;
        }
        
        if(!confirmPassword.equals(password)) {
            config.showErrorMessage("Passwords does not match", "Password Error", currentStage);
            return true;
        }
        
        return false;
    }
    
    private boolean checkPassword(Stage currentStage, String password, String confirmPassword) {
        
        return true;
    }

    @FXML
    private void backButtonMouseClickHandler(MouseEvent event) {
        returnToPreviousPage();
    }

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String fName = firstNameField.getText();
        String mName = middleNameField.getText();
        String lName = lastNameField.getText();
        String eAddress = emailAddressField.getText();
        String pNumber = phoneNumberField.getText();
        String pass = passwordField.getText();
        String confirmPass = confirmPasswordField.getText();
        
        String sql = "UPDATE user SET first_name = ?, middle_name = ?, last_name = ?, phone_number = ?, email = ? WHERE username = ?";
        
        if(verifyInput(currentStage, fName, lName, eAddress, pNumber, pass, confirmPass)) 
            return;
        
        if(db.updateData(sql, fName, mName, lName, pNumber, eAddress, username)) {
                System.out.println("User updated successfully!");
                config.showAlert(Alert.AlertType.INFORMATION, "User Update", "User Updated Succesfully!", currentStage);
                returnToPreviousPage();
        }
        
        
    }
    
}
