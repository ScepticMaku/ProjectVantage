/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.models.RecentActivity;
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
    private TableView<RecentActivity> activityTable;
    @FXML
    private TableColumn<RecentActivity, String> dateColumn;
    @FXML
    private TableColumn<RecentActivity, String> userColumn;
    @FXML
    private TableColumn<RecentActivity, String> actionColumn;

    private ObservableList<RecentActivity> activityLogs;

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
        
        dateColumn.setResizable(false);
        userColumn.setResizable(false);
        actionColumn.setResizable(false);

        // Initialize the activity table
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));

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
        String sql = "SELECT COUNT(*) AS total FROM task WHERE status_id = 2";
        
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
        // Load dashboard statistics
        totalUsers = getTotalUsers();
        totalActiveProjects = getActiveProjects();
        completedTasks = getCompletedTasks();
        
        totalUsersLabel.setText(String.valueOf(totalUsers));
        activeProjectsLabel.setText(String.valueOf(totalActiveProjects));
        completedTasksLabel.setText(String.valueOf(completedTasks));

        // Clear existing activity logs
        activityLogs.clear();
        
        // Load recent activities from system_logs table
        String sql = "SELECT user.username, system_log.action, system_log.timestamp " +
                    "FROM system_log " +
                    "INNER JOIN user ON system_log.user_id = user.id " +
                    "ORDER BY system_log.timestamp DESC LIMIT 10";
        
        try (ResultSet result = db.getData(sql)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            while (result.next()) {
                String timestamp = result.getTimestamp("timestamp").toLocalDateTime().format(formatter);
                String username = result.getString("username");
                String action = result.getString("action");
                
                activityLogs.add(new RecentActivity(timestamp, username, action));
            }
            
            activityTable.setItems(activityLogs);
        } catch (Exception e) {
            System.out.println("Error loading activity logs: " + e.getMessage());
            e.printStackTrace();
        }
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
}
