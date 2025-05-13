/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.utility.dbConnect;
import projectvantage.utility.SessionConfig;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * FXML Controller class
 *
 * @author PC15
 */
public class AdminDashboardPageController implements Initializable {
    
    private static AdminDashboardPageController instance;
    
    dbConnect db = new dbConnect();
    
    private int totalUsers;
    private int totalActiveProjects;
    private int completedTasks;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label usernameLabel;
    @FXML
    private Group welcomeMessageLabel;
    @FXML
    private Label totalUsersLabel;
    @FXML
    private Label activeProjectsLabel;
    @FXML
    private Label completedTasksLabel;
    @FXML
    private TableView<ActivityLog> activityTable;
    @FXML
    private TableColumn<ActivityLog, String> dateColumn;
    @FXML
    private TableColumn<ActivityLog, String> userColumn;
    @FXML
    private TableColumn<ActivityLog, String> actionColumn;
    @FXML
    private TableColumn<ActivityLog, String> statusColumn;

    private ObservableList<ActivityLog> activityLogs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            SessionConfig sessionConf = SessionConfig.getInstance();
            
            loadContent(sessionConf.getUsername());
        });
        
        dateColumn.setSortable(false);
        userColumn.setSortable(false);
        actionColumn.setSortable(false);
        statusColumn.setSortable(false);
        
        dateColumn.setResizable(false);
        userColumn.setResizable(false);
        actionColumn.setResizable(false);
        statusColumn.setResizable(false);

        // Initialize the activity table
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        activityLogs = FXCollections.observableArrayList();
        activityTable.setItems(activityLogs);

        // Load initial data
        loadDashboardData();
    }    
    
    public static AdminDashboardPageController getInstance() {
        return instance;
    }
    
    public void loadContent(String user) {
        usernameLabel.setText(user);
    }
    
    private int getTotalUsers() {
        String sql = "SELECT count(*) AS total FROM user";
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    private int getCompletedTasks() {
        String sql = "SELECT COUNT(*) AS total FROM task";
        
        try (ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    
    private int getActiveProjects() {
        String sql = "SELECT COUNT(*) AS total FROM project";
        
        try (ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    private void loadDashboardData() {
        // TODO: Replace with actual data from your backend
        
        totalUsers = getTotalUsers();
        totalActiveProjects = getActiveProjects();
        completedTasks = getCompletedTasks();
        
        totalUsersLabel.setText(String.valueOf(totalUsers));
        activeProjectsLabel.setText(String.valueOf(totalActiveProjects));
        completedTasksLabel.setText(String.valueOf(completedTasks));

        // Add some sample activity logs
        activityLogs.add(new ActivityLog(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
            "John Doe",
            "Created new project 'Website Redesign'",
            "Completed"
        ));
        activityLogs.add(new ActivityLog(
            LocalDateTime.now().minusHours(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
            "Jane Smith",
            "Updated project status",
            "In Progress"
        ));
    }

    @FXML
    private void handleDashboardClick(ActionEvent event) {
        // TODO: Implement dashboard view
    }

    @FXML
    private void handleUsersClick(ActionEvent event) {
        // TODO: Implement users view
    }

    @FXML
    private void handleProjectsClick(ActionEvent event) {
        // TODO: Implement projects view
    }

    @FXML
    private void handleReportsClick(ActionEvent event) {
        // TODO: Implement reports view
    }

    @FXML
    private void handleSettingsClick(ActionEvent event) {
        // TODO: Implement settings view
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        // TODO: Implement logout functionality
    }

    // Activity Log model class
    public static class ActivityLog {
        private final String date;
        private final String user;
        private final String action;
        private final String status;

        public ActivityLog(String date, String user, String action, String status) {
            this.date = date;
            this.user = user;
            this.action = action;
            this.status = status;
        }

        public String getDate() { return date; }
        public String getUser() { return user; }
        public String getAction() { return action; }
        public String getStatus() { return status; }
    }
}
