/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.utility.PageConfig;

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
    
    private String username;
    private String role;
    
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
    
    public void loadUser(String...info) {
        username = info[5];
        role = info[6];
        
        switch(role) {
            case "admin":
                editUserButton.setVisible(false);
                break;
        }
        
        firstNamePlaceholder.setText(info[0]);
        middleNamePlaceholder.setText(info[1]);
        lastNamePlaceholder.setText(info[2]);
        emailAddressPlaceholder.setText(info[3]);
        phoneNumberPlaceholder.setText(info[4]);
        usernamePlaceholder.setText(info[5]);
        rolePlaceholder.setText(info[6]);
        statusPlaceholder.setText(info[7]);
        
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
