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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadUser();
    }
    
    private void loadUser() {
        dbConnect db = new dbConnect();
        
        String username = main.getUsername();
        String sql = "SELECT first_name, middle_name, last_name, email, phone_number, username, role FROM user WHERE username = '"+ username +"'";
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                firstNamePlaceholder.setText(result.getString("first_name"));
                middleNamePlaceholder.setText(result.getString("middle_name"));
                lastNamePlaceholder.setText(result.getString("last_name"));
                emailAddressPlaceholder.setText(result.getString("email"));
                phoneNumberPlaceholder.setText(result.getString("phone_number"));
                usernamePlaceholder.setText(result.getString("username"));
                rolePlaceholder.setText(result.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
    
}
