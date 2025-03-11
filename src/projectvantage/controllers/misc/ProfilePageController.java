/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.controllers.authentication.LoginController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectvantage.controllers.team_member.TeamMemberMainPageController;
import projectvantage.models.User;
import projectvantage.utility.dbConnect;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class ProfilePageController implements Initializable {
    
    TeamMemberMainPageController main = TeamMemberMainPageController.getInstance();
    private static ProfilePageController instance;
    
    private String username;

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
        firstNamePlaceholder.setText(info[0]);
        middleNamePlaceholder.setText(info[1]);
        lastNamePlaceholder.setText(info[2]);
        emailAddressPlaceholder.setText(info[3]);
        phoneNumberPlaceholder.setText(info[4]);
        usernamePlaceholder.setText(info[5]);
        rolePlaceholder.setText(info[6]);
    }
}
