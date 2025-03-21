/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.Config;
import projectvantage.models.User;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.controllers.team_member.TeamMemberMainPageController;
import projectvantage.models.User;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class ProfilePageController implements Initializable {
    
    PageConfig pageConf = new PageConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    Config config = new Config();
            
    private static ProfilePageController instance;
    
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String role;

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
    private AnchorPane rootPane;
    @FXML
    private Button editProfileButton;
    @FXML
    private Button changePasswordButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }
    
    public static ProfilePageController getInstance() {
        return instance;
    }
    
    public void loadUser(String userInput) {
        
        User user = dbConf.getUserByUsername(userInput);
        
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        lastName = user.getLastName();
        emailAddress = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        username = userInput;
        role = user.getRole();
        
        firstNamePlaceholder.setText(firstName);
        middleNamePlaceholder.setText(middleName);
        lastNamePlaceholder.setText(lastName);
        emailAddressPlaceholder.setText(emailAddress);
        phoneNumberPlaceholder.setText(phoneNumber);
        usernamePlaceholder.setText(username);
        rolePlaceholder.setText(role);
    }
    
    public void loadChangePasswordPage(Class getClass, Event evt, String targetFXML) throws Exception {
        Parent root = FXMLLoader.load(getClass.getResource(targetFXML));
        Stage stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        pageConf.setCenterAlignment(stage);
        
        ChangePasswordPageController.getInstance().setUsername(username);
        
        stage.show();
    }

    @FXML
    private void editProfileMouseClickHandler(MouseEvent event) throws Exception {
        
        TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
        AdminPageController adminController = AdminPageController.getInstance();
        
        String FXML = "/projectvantage/fxml/misc/EditProfilePage.fxml";
        
        switch(role) {
            case "team member":
                pageConf.loadEditProfilePage(FXML, teamMemberController.getRootPane(), username);
                break;
            case "admin":
                pageConf.loadEditProfilePage(FXML, adminController.getRootPane(), username);
                break;
        }
    }

    @FXML
    private void changePasswordMouseClickHandler(MouseEvent event) throws Exception {
        String FXML = "/projectvantage/fxml/misc/ChangePasswordPage.fxml";
        loadChangePasswordPage(getClass(),event, FXML);
    }

}
