/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_member;

import projectvantage.models.TeamMember;
import projectvantage.models.User;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.Team;
import projectvantage.utility.dbConnect;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class ViewTeamMemberPageController implements Initializable {

    private static ViewTeamMemberPageController instance;
    
    DatabaseConfig databaseConf = new DatabaseConfig();
    dbConnect db = new dbConnect();
    
    private String lastName;
    private String username;
    private String role;
    private String status;
    private String teamName;
    
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label teamNameLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            lastNameLabel.setText(lastName);
            usernameLabel.setText(username);
            roleLabel.setText(role);
            statusLabel.setText(status);
            teamNameLabel.setText(teamName);
        });
    }    
    
    public static ViewTeamMemberPageController getInstance() {
        return instance;
    }
    
    public void loadContent(int userId) {
        User user = databaseConf.getUserById(userId);
        TeamMember member = databaseConf.getTeamMemberByUserId(userId);
        Team team = databaseConf.getTeamById(member.getTeamId());
        
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.role = member.getRole();
        this.status = member.getStatus();
        this.teamName = team.getName();
    }    
}
