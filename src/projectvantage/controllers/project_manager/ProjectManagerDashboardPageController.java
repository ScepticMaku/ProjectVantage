/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.project_manager;

import projectvantage.models.ProjectActivity;
import projectvantage.utility.dbConnect;
import projectvantage.utility.SessionConfig;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.format.DateTimeFormatter;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class ProjectManagerDashboardPageController implements Initializable {
    
    private static ProjectManagerDashboardPageController instance;
    
    dbConnect db = new dbConnect();
    
    ObservableList<ProjectActivity> activityLogs = FXCollections.observableArrayList();
    
    private int userId;
    private String username;
    
    @FXML
    private Label usernameLabel;
    @FXML
    private Label activeProjectsLabel;
    private Label teamMembersLabel;
    @FXML
    private Label tasksProgressLabel;
    @FXML
    private TableView<ProjectActivity> projectActivityTable;
    @FXML
    private TableColumn<ProjectActivity, String> dateColumn;
    @FXML
    private TableColumn<ProjectActivity, String> projectColumn;
    @FXML
    private TableColumn<ProjectActivity, String> activityColumn;
    @FXML
    private AnchorPane rootPane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        
        // Configure table columns
        dateColumn.setSortable(false);
        projectColumn.setSortable(false);
        activityColumn.setSortable(false);
        
        dateColumn.setResizable(false);
        projectColumn.setResizable(false);
        activityColumn.setResizable(false);

        // Initialize the activity table
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));

         Platform.runLater(() -> {
            SessionConfig sessionConf = SessionConfig.getInstance();
            
            userId = sessionConf.getId();
            username = sessionConf.getUsername();
            
            loadDashboardData();
        });
    }    
    
    public static ProjectManagerDashboardPageController getInstance() {
        return instance;
    }
    
    public void loadContent() {
        usernameLabel.setText(username);
    }
    
    private int getProjects() {
        String sql = "SELECT COUNT(*) AS total FROM project WHERE user_id = " + userId;
        
        try (ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    private int getTeamMembers() {
        String sql = "SELECT COUNT(*) AS total FROM team_member";
        
        try (ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    private int getTotalTasks() {
        String sql = "SELECT COUNT(*) AS total FROM task "
                + "INNER JOIN project ON project_id = project.id "
                + "WHERE project.user_id = " + userId;
        
        try (ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    private void loadDashboardData() {
        // Load dashboard statistics
        int activeProjects = getProjects();
        int totalTasks = getTotalTasks();
        
        activeProjectsLabel.setText(String.valueOf(activeProjects));
        tasksProgressLabel.setText(String.valueOf(totalTasks));

        // Clear existing activity logs
        if(activityLogs != null) {
            activityLogs.clear();
        }
        
        // Load recent activities from project_log table
        String sql = "SELECT project_log.timestamp, project.name as project_name, project_log.action " +
                    "FROM project_log " +
                    "INNER JOIN project ON project_log.project_id = project.id " +
                    "ORDER BY project_log.timestamp DESC LIMIT 10";
        
        try (ResultSet result = db.getData(sql)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            while (result.next()) {
                activityLogs.add(new ProjectActivity(
                        result.getTimestamp("timestamp").toLocalDateTime().format(formatter),
                        result.getString("project_name"),
                        result.getString("action")
                ));
            }
            projectActivityTable.setItems(activityLogs);
        } catch (Exception e) {
            System.out.println("Error loading activity logs: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
