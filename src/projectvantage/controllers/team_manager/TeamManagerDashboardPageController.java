/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_manager;

import projectvantage.utility.SessionConfig;
import projectvantage.models.TeamActivity;
import projectvantage.utility.dbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class TeamManagerDashboardPageController implements Initializable {
    
    private static TeamManagerDashboardPageController instance;
    
    dbConnect db = new dbConnect();
    
    ObservableList<TeamActivity> teamActivities = FXCollections.observableArrayList();
    
    private String username;
    
    @FXML
    private Label usernameLabel;
    @FXML
    private Label teamMembersLabel;
    @FXML
    private TableView<TeamActivity> teamActivityTable;
    @FXML
    private TableColumn<TeamActivity, String> dateColumn;
    @FXML
    private Label totalTeamsLabel;
    @FXML
    private TableColumn<TeamActivity, String> activityColumn;
    @FXML
    private TableColumn<TeamActivity, String> teamColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        
         // Configure table columns
        dateColumn.setSortable(false);
        teamColumn.setSortable(false);
        activityColumn.setSortable(false);
        
        dateColumn.setResizable(false);
        teamColumn.setResizable(false);
        activityColumn.setResizable(false);

        // Initialize the activity table
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        
        Platform.runLater(() -> {
            SessionConfig sessionConf = SessionConfig.getInstance();
            username = sessionConf.getUsername();
            
            usernameLabel.setText(username);
            
            loadDashboardData();
        });
    }    
    
    private int getTeamMembers() {
        String sql = "SELECT COUNT(*) AS total FROM team_member";
        try (ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    private int getTotalTeams() {
        String sql = "SELECT COUNT(*) AS total FROM team";
        try (ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    private void initializeTableColumns() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
    }
    
    private void loadDashboardData() {
        String teamMembers = String.valueOf(getTeamMembers());
        String totalTeams = String.valueOf(getTotalTeams());
        
        teamMembersLabel.setText(teamMembers);
        totalTeamsLabel.setText(totalTeams);
        
        String sql = "SELECT team_log.timestamp, team.name as team_name, team_log.action " +
                    "FROM team_log " +
                    "INNER JOIN team ON team_log.team_id = team.id " +
                    "ORDER BY team_log.timestamp DESC LIMIT 10";
        
        try(ResultSet result = db.getData(sql)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            while(result.next()) {
                teamActivities.add(new TeamActivity(
                    result.getTimestamp("timestamp").toLocalDateTime().format(formatter),
                    result.getString("team_name"),
                    result.getString("action")
                ));
                teamActivityTable.setItems(teamActivities);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static TeamManagerDashboardPageController getInstance() {
        return instance;
    }
    
//    public void loadContent(String user) {
//        usernameLabel.setText(user);
//    }
    
}
