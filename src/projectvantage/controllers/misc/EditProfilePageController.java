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
import projectvantage.utility.AlertConfig;
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
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    Config config = new Config();
    DatabaseConfig dbConf = new DatabaseConfig();
    
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
    
    public void loadUserContents(String userInput) {
        
        User user = dbConf.getUserByUsername(userInput);
        
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        lastName = user.getLastName();
        emailAddress = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        username = user.getUsername();
        role = user.getRole();
        
        firstNameField.setText(firstName);
        middleNameField.setText(middleName);
        lastNameField.setText(lastName);
        emailAddressField.setText(emailAddress);
        phoneNumberField.setText(phoneNumber);
        usernamePlaceholder.setText(username);
        rolePlaceholder.setText(role);
    }
    
    private void returnToPreviousPage() throws Exception {
        
        String fxmlLocation = "/projectvantage/fxml/misc/ProfilePage.fxml";
        
        TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
        AdminPageController adminController = AdminPageController.getInstance();
        
        switch(role) {
            case "team member":
                pageConf.loadProfilePage(fxmlLocation, username, teamMemberController.getBackgroundPane(), teamMemberController.getRootPane());
                break;
            case "admin":
                pageConf.loadProfilePage(fxmlLocation, username, adminController.getBackgroundPane(), adminController.getRootPane());
                break;
        }
    }
    
    private boolean isEmailDuplicated(String emailInput, String storedEmail) throws SQLException {
        return emailInput.equals(storedEmail);
    }
    
    private boolean verifyInput(Stage currentStage, String fName, String lName, String eAddress, String pNumber, String password, String confirmPassword) throws Exception {
        if(fName.isEmpty()) {
            alertConf.showEditProfileErrorAlert(currentStage, "First name field must not be empty.");
            return true;
        }
        
        if(lName.isEmpty()) {
            alertConf.showEditProfileErrorAlert(currentStage, "Last name field must not be empty.");
            return true;
        }
        
        if(eAddress.isEmpty()) {
            alertConf.showEditProfileErrorAlert(currentStage, "Email address field must not be empty.");
            return true;
        }
        
        if(pNumber.isEmpty()) {
            alertConf.showEditProfileErrorAlert(currentStage, "Phone number field must not be empty.");
            return true;
        }
        
        if(!config.isValidEmailFormat(eAddress)) {
            alertConf.showEditProfileErrorAlert(currentStage, "Email format is invalid.");
            return true;
        }
        
        if(config.isValidPhoneNumber(pNumber) || !config.isValidPhoneNumberFormat(pNumber)) {
            alertConf.showEditProfileErrorAlert(currentStage, "Phone number is invalid.");
            return true;
        }
        
        if(isEmailDuplicated("email", eAddress)) {
            alertConf.showEditProfileErrorAlert(currentStage, "Email already exists.");
            return true;
        }
        
        if(password.isEmpty()) {
            alertConf.showEditProfileErrorAlert(currentStage, "You must enter password to submit changes.");
            return true;
        }
        
//        if(!getPassword(username).equals(password)) {
//            config.showErrorMessage("Incorrect password", "Password Error", currentStage);
//            return true;
//        }
        
        if(!confirmPassword.equals(password)) {
            alertConf.showEditProfileErrorAlert(currentStage, "Password does not match.");
            return true;
        }
        return false;
    }
    
    private boolean checkPassword(Stage currentStage, String password, String confirmPassword) {
        
        return true;
    }

    @FXML
    private void backButtonMouseClickHandler(MouseEvent event) throws Exception {
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
                alertConf.showAlert(Alert.AlertType.INFORMATION, "User Update Successful", "User Updated Succesfully!", currentStage);
                returnToPreviousPage();
        }
        
        
    }
    
}
