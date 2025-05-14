/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_member;

import projectvantage.models.TeamMember;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.SessionConfig;
import projectvantage.models.TeamMemberActivity;
import projectvantage.utility.dbConnect;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class TeamMemberDashboardPageController implements Initializable {
    
    private static TeamMemberDashboardPageController instance;
    
    dbConnect db = new dbConnect();
    DatabaseConfig databaseConf = new DatabaseConfig();
    
    ObservableList<TeamMemberActivity> teamMemberActivities = FXCollections.observableArrayList();

    private int userId;
    private int teamMemberId;
    private String username;
    
    @FXML
    private Label usernameLabel;
    @FXML
    private Label completedTasksLabel;
    @FXML
    private TableColumn<TeamMemberActivity, String> taskNameColumn;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label currentTaskNameLabel;
    @FXML
    private Label currentProjectLabel;
    @FXML
    private Label currentDueDateLabel;
    @FXML
    private Button viewTaskButton;
    @FXML
    private TableView<TeamMemberActivity> completedTasksTable;
    @FXML
    private TableColumn<TeamMemberActivity, String> completedDateColumn;
    @FXML
    private TableColumn<TeamMemberActivity, String> projectNameColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        completedDateColumn.setSortable(false);
        taskNameColumn.setSortable(false);
        projectNameColumn.setSortable(false);
        
        completedDateColumn.setResizable(false);
        taskNameColumn.setResizable(false);
        projectNameColumn.setResizable(false);
        
        completedDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
        
        Platform.runLater(() -> {
            SessionConfig sessionConf = SessionConfig.getInstance();
            username = sessionConf.getUsername();
            userId = sessionConf.getId();
            
            TeamMember member = databaseConf.getTeamMemberByUserId(userId);
            
            if(member != null) {
                teamMemberId = member.getId();
            }
            
            usernameLabel.setText(username);
            
            loadDashboardData();
            loadCurrentTask();
        });
        
    }    
    
    public static TeamMemberDashboardPageController getInstance() {
        return instance;
    }
    
    private void loadCurrentTask() {
        String sql = "SELECT task.name as task_name, project.name AS project_name, task.due_date "
                + "FROM task "
                + "INNER JOIN project ON task.project_id = project.id "
                + "WHERE team_member_id = " + teamMemberId;
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                currentTaskNameLabel.setText(result.getString("task_name"));
                currentProjectLabel.setText(result.getString("project_name"));
                currentDueDateLabel.setText(result.getString("due_date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int getCompletedTasks() {
        String sql = "SELECT COUNT(*) as total FROM task WHERE team_member_id = " + teamMemberId + " AND status_id = 2";
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    private void loadDashboardData() {
        String completedTasks = String.valueOf(getCompletedTasks());
        
        completedTasksLabel.setText(completedTasks);
        
        String sql = "SELECT task.date_completed, task.name AS task_name, project.name AS project_name "
                + "FROM `task` INNER JOIN project ON task.project_id = project.id "
                + "WHERE task.status_id = 2 AND team_member_id = " + teamMemberId + " LIMIT 10";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                teamMemberActivities.add(new TeamMemberActivity(
                        result.getDate("date_completed").toString(),
                        result.getString("task_name"),
                        result.getString("project_name")
                ));
            }
            completedTasksTable.setItems(teamMemberActivities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewTaskButtonMouseClickHandler(MouseEvent event) {
    }
}
