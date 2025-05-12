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
import projectvantage.utility.PageConfig;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.controllers.project_manager.ProjectManagerPageController;
import projectvantage.controllers.project_manager.ViewProjectPageController;
import projectvantage.controllers.team_manager.TeamManagerPageController;
import projectvantage.controllers.team_manager.ViewTeamPageController;
import projectvantage.utility.SessionConfig;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class ViewTeamMemberPageController implements Initializable {

    private static ViewTeamMemberPageController instance;
    
    PageConfig pageConf = new PageConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    dbConnect db = new dbConnect();
    
    ObservableList<String> recentActivities = FXCollections.observableArrayList();
    
    private int teamId;
    private int teamMemberId;
    private String lastName;
    private String username;
    private String role;
    private String status;
    private String teamName;
    private String userRole;
    
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
    @FXML
    private Button viewTeamButton;
    @FXML
    private Button viewUserButton;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            SessionConfig sessionConf = SessionConfig.getInstance();
            
            userRole = sessionConf.getRole();
            
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
        
        teamId = member.getTeamId();
        teamMemberId = member.getId();
        
        Team team = databaseConf.getTeamById(member.getTeamId());
        
        lastName = user.getLastName();
        username = user.getUsername();
        role = member.getRole();
        status = member.getStatus();
        teamName = team.getName();
        
        loadLogs();
    }    
    
    private void loadLogs() {
        String sql = "SELECT team_member_log.id AS id, user.username AS username, description, timestamp "
                + "FROM team_member_log INNER JOIN user ON team_member_log.user_id = user.id WHERE team_member_log.team_member_id = " + teamMemberId + " "
                + "ORDER BY team_member_log.id DESC LIMIT 10";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                String log = "[USER]: " + result.getString("username")  + ": " + result.getString("description") + " - [TIMESTAMP]: " + result.getTimestamp("timestamp");
                recentActivities.add(log);
            }
            listView.setItems(recentActivities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewTeamMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        AdminPageController adminController = AdminPageController.getInstance();
        TeamManagerPageController teamManagerController = TeamManagerPageController.getInstance();
        ProjectManagerPageController projectManagerController = ProjectManagerPageController.getInstance();
        TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
        
        String teamFXML = "/projectvantage/fxml/team_manager/ViewTeamPage.fxml";
        
        switch(userRole) {
            case "admin":
                adminController.loadPage(teamFXML, "Team");
                ViewTeamPageController.getInstance().loadContent(teamId);
                break;
            case "project manager":
                projectManagerController.loadPage(teamFXML, "Team");
                ViewTeamPageController.getInstance().loadContent(teamId);
                break;
            case "team manager":
                teamManagerController.loadPage(teamFXML, "Team");
                ViewTeamPageController.getInstance().loadContent(teamId);
                break;
            case "standard":
                teamMemberController.loadPage(teamFXML, "Team");
                ViewTeamPageController.getInstance().loadContent(teamId);
                break;
        }
        currentStage.close();
    }

    @FXML
    private void viewUserMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        AdminPageController adminController = AdminPageController.getInstance();
        TeamManagerPageController teamManagerController = TeamManagerPageController.getInstance();
        ProjectManagerPageController projectManagerController = ProjectManagerPageController.getInstance();
        TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
        
        String userFXML = "/projectvantage/fxml/admin/AdminUserPage.fxml";
        
        switch(userRole) {
            case "admin":
                pageConf.loadUserPage(userFXML, username, rootPane, adminController.getRootPane());
                currentStage.close();
            break;
            case "team manager":
                pageConf.loadUserPage(userFXML, username, rootPane, teamManagerController.getBackgroundPane());
                currentStage.close();
            break;
            case "project manager":
                pageConf.loadUserPage(userFXML, username, rootPane, projectManagerController.getBackgroundPane());
                currentStage.close();
            break;
            case "standard":
                pageConf.loadUserPage(userFXML, username, rootPane, teamMemberController.getBackgroundPane());
                currentStage.close();
            break;
        }
        
    }
}
