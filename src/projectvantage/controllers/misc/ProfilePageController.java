/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.controllers.authentication.LoginController;
import projectvantage.utility.dbConnect;
import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.Config;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void loadUser(String...info) {
        firstName = info[0];
        middleName = info[1];
        lastName = info[2];
        emailAddress = info[3];
        phoneNumber = info[4];
        username = info[5];
        role = info[6];
        
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
        
        ChangePasswordPageController.getInstance().setEmail(emailAddress);
        ChangePasswordPageController.getInstance().setUsername(username);
        
        stage.show();
    }

    @FXML
    private void editProfileMouseClickHandler(MouseEvent event) throws Exception {
        String FXML = "/projectvantage/fxml/misc/EditProfilePage.fxml";
        
        switch(dbConf.getRole(username)) {
            case "team member":
                TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
                pageConf.loadEditProfilePage(FXML, teamMemberController.getBackgroundPane(), teamMemberController.getRootPane(),
                        firstName, middleName, lastName, emailAddress, phoneNumber, username, role);
                break;
            case "admin":
                AdminPageController adminController = AdminPageController.getInstance();
                pageConf.loadEditProfilePage(FXML,adminController.getBackgroundPane(), adminController.getRootPane(),
                        firstName, middleName, lastName, emailAddress, phoneNumber, username, role);
                break;
        }
    }

    @FXML
    private void changePasswordMouseClickHandler(MouseEvent event) throws Exception {
        String FXML = "/projectvantage/fxml/misc/ChangePasswordPage.fxml";
//        TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
//        AdminPageController adminController = AdminPageController.getInstance();
        
        pageConf.switchToAuthenticator(event, getClass(), rootPane, FXML, "Change Password", emailAddress);
        
//        switch(dbConf.getRole(username)) {
//            case "team member":
////                loadChangePasswordPage(FXML, teamMemberController.getBackgroundPane(), teamMemberController.getRootPane(), username, role);
//                break;
//            case "admin":
//                loadChangePasswordPage(FXML, adminController.getBackgroundPane(), adminController.getRootPane(), username, role);
//                break;
//        }
    }

}
