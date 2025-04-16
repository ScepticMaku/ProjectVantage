/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;
import projectvantage.utility.ElementConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    ElementConfig elementConf = new ElementConfig();
    
    private static final double IMAGE_SIZE = 100;
    
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
    private Label rolePlaceholder;
    @FXML
    private Label statusPlaceholder;
    @FXML
    private Button editUserButton;
    @FXML
    private Button backButton;
    @FXML
    private ImageView userPhoto;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            elementConf.loadProfilePicture(username, userPhoto, IMAGE_SIZE);
        });
    }
    
    public static AdminUserPageController getInstance() {
        return instance;
    }
    
    public ImageView getUserPhoto() {
        return userPhoto;
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
        usernamePlaceholder.setText(username);
        rolePlaceholder.setText(role);
        statusPlaceholder.setText(status);
    }

    @FXML
    private void editUserButtonMouseClickHandler(MouseEvent event) throws Exception {
        String FXML = "/projectvantage/fxml/admin/EditUserPage.fxml";
        pageConf.loadWindow(FXML, "Edit User", rootPane);
        EditUserPageController.getInstance().loadUserContents(username);
    }

    @FXML
    private void backButtonMouseClickHandler(MouseEvent event) {
        AdminPageController adminController = AdminPageController.getInstance();
        adminController.loadPage("/projectvantage/fxml/admin/UserManagementPage.fxml", "Users");
    }
}
