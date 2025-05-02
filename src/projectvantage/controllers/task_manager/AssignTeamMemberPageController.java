/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.task_manager;

import projectvantage.models.Team;
import projectvantage.models.TeamMember;
import projectvantage.utility.dbConnect;
import projectvantage.utility.AlertConfig;
import projectvantage.controllers.task_manager.ViewTaskPageController;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class AssignTeamMemberPageController implements Initializable {
    
    private static AssignTeamMemberPageController instance;
    
    dbConnect db = new dbConnect();
    AlertConfig alertConf = new AlertConfig();
    
    ObservableList<Team> teamList = FXCollections.observableArrayList();
    ObservableList<TeamMember> teamMemberList = FXCollections.observableArrayList();
    
    private int projectId;
    private int teamId;
    private int taskId;
    
    @FXML
    private TableView<Team> teamTable;
    @FXML
    private TableColumn<Team, String> teamColumn;
    @FXML
    private TableView<TeamMember> memberTable;
    @FXML
    private TableColumn<TeamMember, String> memberColumn;
    @FXML
    private Button assignButton;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        teamColumn.setSortable(false);
        memberColumn.setSortable(false);
        
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
        Platform.runLater(() -> {
            refreshTeamTable();
            
            teamTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                teamMemberList.clear();
                loadTeamMemberTable(newSelection.getId());
            });
        });
    }    
    
    public static AssignTeamMemberPageController getInstance() {
        return instance;
    }
    
    public void setProjectId(int id) {
        this.projectId = id;
    }
    
    public void setTaskId(int id) {
        this.taskId = id;
    }
    
    public void refreshTeamTable() {
        teamList.clear();
        loadTeamTable();
    }
    
    public void loadTeamTable() {
        String sql = "SELECT id, name, project_id FROM team WHERE project_id = " + projectId + " ORDER BY id DESC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                teamList.add(new Team(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("project_id")
                ));
            }
            
            teamTable.setItems(teamList);
            
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void loadTeamMemberTable(int id) {
        String sql = "SELECT team_member.id AS id, team_id, user.last_name AS last_name, user.username AS username, team_member_role.name AS role, team_member_status.name AS status "
                + "FROM team_member INNER JOIN user ON user_id = user.id INNER JOIN team_member_role ON team_member.role_id = team_member_role.id "
                + "INNER JOIN team_member_status ON team_member.status_id = team_member_status.id WHERE team_id = " + id;
        
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
    
    private boolean isMemberAlreadyAssigned(int id) {
        String sql = "SELECT * FROM task WHERE team_member_id = " + id;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void assignButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        TeamMember teamMember = memberTable.getSelectionModel().getSelectedItem();
        
        int memberId = teamMember.getId();
        
        if(isMemberAlreadyAssigned(memberId)) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Error Assigning a Task", "This member is already assigned to a task.", currentStage);
            return;
        }
        
        String sql = "UPDATE task SET team_member_id = ? WHERE id = ?";
        
        if(db.executeQuery(sql, memberId, taskId)) {
            System.out.println("Task updated successfully!|");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Member Successfully Assigned!", "Assign success!", currentStage);
            currentStage.close();
            ViewTaskPageController.getInstance().loadContent(taskId);
            ViewTaskPageController.getInstance().load();
        }
    }
    
}
