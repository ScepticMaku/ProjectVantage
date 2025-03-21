/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class AdminUserPageController implements Initializable {
    
    private static AdminUserPageController instance;
    
    PageConfig pageConf = new PageConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String role;
    private String status;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label usernamePlaceholder;
    @FXML
    private Label firstNamePlaceholder;
    @FXML
    private Label middleNamePlaceholder;
    @FXML
    private Label lastNamePlaceholder;
    @FXML
    private Label emailAddressPlaceholder;
    @FXML
    private Label phoneNumberPlaceholder;
    @FXML
    private Label rolePlaceholder;
    @FXML
    private Label statusPlaceholder;
    @FXML
    private Button editUserButton;
    @FXML
    private Button backButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }
    
    public static AdminUserPageController getInstance() {
        return instance;
    }
    
    public void loadUser(String userInput) {
        
        User user = dbConf.getUserByUsername(userInput);
        
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        lastName = user.getLastName();
        email = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        username = user.getUsername();
        role = user.getRole();
        status = user.getStatus();
        
        switch(role) {
            case "admin":
                editUserButton.setVisible(false);
                break;
        }
        
        firstNamePlaceholder.setText(firstName);
        middleNamePlaceholder.setText(middleName);
        lastNamePlaceholder.setText(lastName);
        emailAddressPlaceholder.setText(email);
        phoneNumberPlaceholder.setText(phoneNumber);
        usernamePlaceholder.setText(username);
        rolePlaceholder.setText(role);
        statusPlaceholder.setText(status);
    }

    @FXML
    private void editUserButtonMouseClickHandler(MouseEvent event) {
        AdminPageController adminController = AdminPageController.getInstance();
        String FXML = "/projectvantage/fxml/admin/EditUserPage.fxml";
        pageConf.loadEditUserPage(FXML, username, adminController.getBackgroundPane(), adminController.getRootPane());
    }

    @FXML
    private void backButtonMouseClickHandler(MouseEvent event) {
        AdminPageController adminController = AdminPageController.getInstance();
        adminController.loadPage("/projectvantage/fxml/admin/UserManagementPage.fxml", "Users");
    }
}
