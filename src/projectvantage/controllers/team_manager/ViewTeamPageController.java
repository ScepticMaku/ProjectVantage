/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_manager;

import projectvantage.controllers.team_member.ViewTeamMemberPageController;
import projectvantage.controllers.team_member.AddTeamMemberPageController;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.utility.LogConfig;
import projectvantage.models.TeamMember;
import projectvantage.models.Project;
import projectvantage.models.Team;
import projectvantage.models.User;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.PageConfig;
import projectvantage.utility.AlertConfig;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectvantage.controllers.project_manager.ProjectManagerPageController;
import projectvantage.controllers.project_manager.ViewProjectPageController;
import projectvantage.controllers.team_member.TeamMemberMainPageController;
import projectvantage.utility.SessionConfig;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class ViewTeamPageController implements Initializable {
    
    private static ViewTeamPageController instance;
    
    PageConfig pageConf = new PageConfig();
    AlertConfig alertConf = new AlertConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    LogConfig logConf = new LogConfig();
    dbConnect db = new dbConnect();
    
    ObservableList<String> recentActivities = FXCollections.observableArrayList();
    ObservableList<TeamMember> teamMemberList = FXCollections.observableArrayList();
    
//    private static final int ROWS_PER_PAGE = 9;
//    private static final double ICON_HEIGHT = 26;
//    private static final double ICON_WIDTH = 26;
    
    private int id;
    private int projectId;
    private int userId;
    private String teamName;
    private String projectName;
    private String teamLeader;
    private String role;
    private String userRole;
//    private String username;
    
    @FXML
    private Label teamNameLabel;
    @FXML
    private Label teamLeaderLabel;
    @FXML
    private Button addLeaderButton;
    @FXML
    private Label projectLabel;
    @FXML
    private TableView<TeamMember> memberTable;
    @FXML
    private TableColumn<TeamMember, Integer> idColumn;
    @FXML
    private TableColumn<TeamMember, String> usernameColumn;
    @FXML
    private TableColumn<TeamMember, String> roleColumn;
    @FXML
    private TableColumn<TeamMember, String> statusColumn;
    @FXML
    private Button addMemberButton;
    @FXML
    private Button viewMemberButton;
    @FXML
    private ListView<String> activityListView;
    @FXML
    private Button deleteTeamButton;
    @FXML
    private Button removeMemberButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button removeLeaderButton;
    @FXML
    private TableColumn<TeamMember, String> lastNameColumn;
    @FXML
    private Button viewProjectButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        load();
    }
    
    public static ViewTeamPageController getInstance() {
        return instance;
    }
    
    public void load() {
        idColumn.setSortable(false);
        usernameColumn.setSortable(false);
        lastNameColumn.setSortable(false);
        roleColumn.setSortable(false);
        statusColumn.setSortable(false);
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        Platform.runLater(() -> {
            refreshTable();
            reloadLog();
            
            memberTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if(newSelection != null && newSelection.getRole().equals("team leader") && userRole.equals("admin") || userRole.equals("project manager")) {
                    removeLeaderButton.setVisible(true);
                } else {
                    removeLeaderButton.setVisible(false);
                }
            });
            
            if(userRole.equals("standard")) {
                addMemberButton.setVisible(false);
                deleteTeamButton.setVisible(false);
                removeMemberButton.setVisible(false);
                addLeaderButton.setVisible(false);
                removeLeaderButton.setVisible(false);
            }
        });
    }
    
    public void loadContent(int id) {
        Team team = databaseConf.getTeamById(id);
        Project project = databaseConf.getProjectById(team.getProjectId());
        SessionConfig sessionConf = SessionConfig.getInstance();
        
        userId = sessionConf.getId();
        userRole = sessionConf.getRole();
        this.id = id;
        teamName = team.getName();
        
        if(databaseConf.getTeamLeaderByTeamId(id) != null) {
            teamLeader = databaseConf.getTeamLeaderByTeamId(id);
            teamLeaderLabel.setText(teamLeader);
            addLeaderButton.setVisible(false);
        }
        
        if(project != null) {
            projectName = project.getName();
            projectLabel.setText(projectName);
            projectId = project.getId();
        }
        
        teamNameLabel.setText(teamName);
    }
    
    public void refreshTable() {
        teamMemberList.clear();
        loadTableData();
    }
    
    public void reloadLog() {
        recentActivities.clear();
        loadLogs();
    }
    
    private void loadLogs() {
        String sql = "SELECT team_log.id AS id, user.username AS username, description, timestamp "
                + "FROM team_log INNER JOIN user ON team_log.user_id = user.id WHERE team_log.team_id = " + id +  " "
                + "ORDER BY team_log.id DESC LIMIT 10";
        
        try(ResultSet result =  db.getData(sql)) {
            while(result.next()) {
                String log = "[USER]: " + result.getString("username")  + ": " + result.getString("description") + " - [TIMESTAMP]: " + result.getTimestamp("timestamp");
                recentActivities.add(log);
            }
            activityListView.setItems(recentActivities);
       } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadTableData() {
        String sql = "SELECT team_member.id AS id, team_id, user.last_name AS last_name, user.username AS username, team_member_role.name AS role, team_member_status.name AS status "
                + "FROM team_member INNER JOIN user ON user_id = user.id INNER JOIN team_member_role ON team_member.role_id = team_member_role.id "
                + "INNER JOIN team_member_status ON team_member.status_id = team_member_status.id WHERE team_id = " + id + " ORDER BY team_member.role_id DESC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                teamMemberList.add(new TeamMember(
                        result.getInt("id"),
                        result.getInt("team_id"),
                        result.getString("last_name"),
                        result.getString("username"),
                        result.getString("role"),
                        result.getString("status")
                ));
            }
            
            memberTable.setItems(teamMemberList);
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private boolean doesLeaderAlreadyExist() {
        String sql = "SELECT id FROM team_member WHERE role_id = 2 AND team_id = " + id;
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database Error: " + e.getMessage());
        }
        return false;
    }

    @FXML
    private void addLeaderButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        int memberId = memberTable.getSelectionModel().getSelectedItem().getId();
        String teamMemberName = memberTable.getSelectionModel().getSelectedItem().getLastName();
        
        if(doesLeaderAlreadyExist()) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Error Adding Leader", "Team leader already exists.", currentStage);
            return;
        }
        
        String sql = "UPDATE team_member SET role_id = 2 WHERE id = ?";
        
        if(db.executeQuery(sql, memberId)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Leader Successfully Assigned!", "Leader assigned successfully!", currentStage);
            logConf.logMakeLeader(userId, projectId, memberId, id, teamMemberName, teamName, projectName);
            
            try {
                AdminPageController adminController = AdminPageController.getInstance();
                TeamManagerPageController teamManagerController = TeamManagerPageController.getInstance();
                
                String viewTeamFXML = "/projectvantage/fxml/team_manager/ViewTeamPage.fxml";
                
                if(userRole.equals("admin")) {
                    adminController.loadPage(viewTeamFXML, "Team");
                    getInstance().loadContent(id);
                    getInstance().load();
                    return;
                }

                if(userRole.equals("team manager")) {
                    teamManagerController.loadPage(viewTeamFXML, "Team");
                    getInstance().loadContent(id);
                    getInstance().load();
                }
            } catch (Exception e) {
                e.printStackTrace();
                alertConf.showAlert(Alert.AlertType.ERROR, "Error Opening a Team", "You must select a team", currentStage);
            } 
        }
    }

    @FXML
    private void addMemberButtonMouseClickHandler(MouseEvent event) throws Exception {
        String memberListFXML = "/projectvantage/fxml/team_member/AddTeamMemberPage.fxml";
        
        pageConf.loadWindow(memberListFXML, "Team Members", rootPane);
        AddTeamMemberPageController.getInstance().loadContent(id);
    }

    @FXML
    private void viewMemberButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        String viewTeamMemberFXML = "/projectvantage/fxml/team_member/ViewTeamMemberPage.fxml";
        
        TeamMember member = memberTable.getSelectionModel().getSelectedItem();
        
        if(member != null) {
            int teamMemberId = member.getId();
            int userId = databaseConf.getUserIdById(teamMemberId);
            pageConf.loadWindow(viewTeamMemberFXML, "Team Member", rootPane);
            ViewTeamMemberPageController.getInstance().loadContent(userId);
            return;
        }
        alertConf.showAlert(Alert.AlertType.ERROR, "Error viewing member", "You must select a member.", currentStage);
    }

    @FXML
    private void deleteTeamButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        String sql = "DELETE FROM team WHERE id = ?";
        String teamPageFXML = "/projectvantage/fxml/team_manager/TeamPage.fxml";
        
        alertConf.showDeleteConfirmationAlert(currentStage, sql, id);
        logConf.logDeleteTeam(userId, teamName);
        
        if(userRole.equals("admin")) {
            AdminPageController adminController = AdminPageController.getInstance();
            adminController.loadPage(teamPageFXML, "Teams");
            return;
        }
        
        if(userRole.equals("team manager")) {
            TeamManagerPageController teamManagerController = TeamManagerPageController.getInstance();
            teamManagerController.loadPage(teamPageFXML, "Teams");
        }
    }


    @FXML
    private void removeMemberButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        TeamMember member = memberTable.getSelectionModel().getSelectedItem();
        
        
        String sql = "DELETE FROM team_member WHERE id = ?";
        
        if(member != null) {
            int teamMemberId = member.getId();
            String teamMemberName = member.getLastName();
            alertConf.showDeleteConfirmationAlert(currentStage, sql, teamMemberId);
            logConf.logRemoveTeamMember(userId, id, teamMemberName, teamName);
            load();
        }
    }

    @FXML
    private void removeLeaderMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        int memberId = memberTable.getSelectionModel().getSelectedItem().getId();
        String teamMemberName = memberTable.getSelectionModel().getSelectedItem().getLastName();
        
        String sql = "UPDATE team_member SET role_id = 1 WHERE id = ?";
        
        if(db.executeQuery(sql, memberId)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Leader Successfully Removed!", "Leader removed successfully!", currentStage);
            logConf.logRemoveLeader(userId, projectId, memberId, id, teamMemberName, teamName, projectName);
            
            try {
                String viewTeamFXML = "/projectvantage/fxml/team_manager/ViewTeamPage.fxml";

                if(userRole.equals("admin")) {
                    AdminPageController.getInstance().loadPage(viewTeamFXML, "Team");
                    getInstance().loadContent(id);
                    getInstance().load();
                    return;
                }

                if(userRole.equals("team manager")) {
                    TeamManagerPageController.getInstance().loadPage(viewTeamFXML, "Team");
                    getInstance().loadContent(id);
                    getInstance().load();
                }
            } catch (Exception e) {
                e.printStackTrace();
                alertConf.showAlert(Alert.AlertType.ERROR, "Error Opening a Team", "You must select a team", currentStage);
            } 
        }
    }

    @FXML
    private void viewProjectButonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        AdminPageController adminController = AdminPageController.getInstance();
        TeamManagerPageController teamManagerController = TeamManagerPageController.getInstance();
        ProjectManagerPageController projectManagerController = ProjectManagerPageController.getInstance();
        TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
        
        String viewProjectFXML = "/projectvantage/fxml/project_manager/ViewProjectPage.fxml";
        
        try {
            switch(userRole) {
                case "admin":
                    adminController.loadPage(viewProjectFXML, projectName);
                    ViewProjectPageController.getInstance().loadContent(projectId);
                break;
                case "team manager":
                    teamManagerController.loadPage(viewProjectFXML, projectName);
                    ViewProjectPageController.getInstance().loadContent(projectId);
                break;
                case "project manager":
                    projectManagerController.loadPage(viewProjectFXML, projectName);
                    ViewProjectPageController.getInstance().loadContent(projectId);
                break;
                case "standard":
                    teamMemberController.loadPage(viewProjectFXML, projectName);
                    ViewProjectPageController.getInstance().loadContent(projectId);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
