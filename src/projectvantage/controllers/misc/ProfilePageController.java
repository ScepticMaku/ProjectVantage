/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.Config;
import projectvantage.utility.ElementConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    ElementConfig elementConf = new ElementConfig();
            
    private static ProfilePageController instance;
    
    private static final double IMAGE_SIZE = 100;
    
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
    private Label rolePlaceholder;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button editProfileButton;
    @FXML
    private Button changePasswordButton;
    @FXML
    private ImageView profilePhoto;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            elementConf.loadProfilePicture(username, profilePhoto, IMAGE_SIZE);
        });
    }
    
    public static ProfilePageController getInstance() {
        return instance;
    }
    
    public ImageView getProfilePhoto() {
        return profilePhoto;
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
        usernamePlaceholder.setText(username);
        rolePlaceholder.setText(role);
        
        Platform.runLater(() -> {
            elementConf.loadProfilePicture(username, profilePhoto, IMAGE_SIZE);
        });
    }

    @FXML
    private void editProfileMouseClickHandler(MouseEvent event) throws Exception {
        String FXML = "/projectvantage/fxml/misc/EditProfilePage.fxml";   
        pageConf.loadWindow(FXML, "Edit Profile", rootPane);
        EditProfilePageController.getInstance().loadUserContents(username);
    }

    @FXML
    private void changePasswordMouseClickHandler(MouseEvent event) throws Exception {
        String FXML = "/projectvantage/fxml/misc/ChangePasswordPage.fxml";
        pageConf.loadWindow(FXML, "Change Password", rootPane);
        ChangePasswordPageController.getInstance().setUsername(username);
    }

}
