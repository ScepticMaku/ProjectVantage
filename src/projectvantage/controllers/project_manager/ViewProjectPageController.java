/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.project_manager;

import projectvantage.models.Project;
import projectvantage.models.Team;
import projectvantage.utility.dbConnect;
import projectvantage.utility.AlertConfig;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.controllers.team_manager.TeamManagerPageController;
import projectvantage.controllers.team_manager.ViewTeamPageController;
import projectvantage.controllers.team_manager.AddTeamPageController;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;
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
public class ViewProjectPageController implements Initializable {
    
    private static ViewProjectPageController instance;
    
    dbConnect db = new dbConnect();
    AlertConfig alertConf = new AlertConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    PageConfig pageConf = new PageConfig();
    
    ObservableList<Team> teamList = FXCollections.observableArrayList();
    
    private int userId;
    private int projectId;
    private String username;
    private String role;
    private String projectName;
    private String description;
    private String creationDate;
    private String dueDate;
    private String status;
    private String creatorName;
    
    
    @FXML
    private Label projectNameLabel;
    @FXML
    private Label descriptionText;
    @FXML
    private Label creationDateLabel;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button viewTeamButton;
    @FXML
    private Button addTeamButton;
    @FXML
    private TableView<Team> teamTable;
    @FXML
    private TableColumn<Team, Integer> teamIdColumn;
    @FXML
    private TableColumn<Team, String> teamNameColumn;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label creatorNameLabel;
    @FXML
    private Button deleteTeamButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        teamIdColumn.setSortable(false);
        teamNameColumn.setSortable(false);
        
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        Platform.runLater(() -> {
            loadTeamTable();
            
            projectNameLabel.setText(projectName);
            descriptionText.setText(description);
            creationDateLabel.setText(creationDate);
            dueDateLabel.setText(dueDate);
            statusLabel.setText(status);
            creatorNameLabel.setText(creatorName);
        });
    }
    
    public static ViewProjectPageController getInstance() {
        return instance;
    }
    
    public void refreshTeamTable() {
        teamList.clear();
        loadTeamTable();
    }
    
    public void loadContent(int projectId, String username) {
        Project project = databaseConf.getProjectById(projectId);
        User user = databaseConf.getUserByUsername(username);
        User creator = databaseConf.getUserById(userId);
        
         this.username = username;
         this.projectId = projectId;
        
        if(project != null) {
            this.projectName = project.getName();
            this.description = project.getDescription();
            this.creationDate = project.getCreationDate();
            this.dueDate = project.getDueDate();
            this.status = project.getStatus();
        }
        
        if(user != null) {
            this.userId = user.getId();
            this.role = user.getRole();
        }
        
        if(creator != null) {
            this.creatorName = creator.getLastName();
        }
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

    @FXML
    private void viewTeamButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        try {
            AdminPageController adminController = AdminPageController.getInstance();
            TeamManagerPageController teamManagerController = TeamManagerPageController.getInstance();

            Team selectedTeam = teamTable.getSelectionModel().getSelectedItem();
            int teamId = selectedTeam.getId();
            String viewTeamFXML = "/projectvantage/fxml/team_manager/ViewTeamPage.fxml";
            
            if(role.equals("admin")) {
                adminController.loadPage(viewTeamFXML, "Team");
                ViewTeamPageController viewTeamController = ViewTeamPageController.getInstance();
                viewTeamController.loadContent(teamId, username);
                return;
            }

            if(role.equals("team manager")) {
                teamManagerController.loadPage(viewTeamFXML, "Team");
                ViewTeamPageController viewTeamController = ViewTeamPageController.getInstance();
                viewTeamController.loadContent(selectedTeam.getId(), username);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    @FXML
    private void addTeamButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/team_manager/AddTeamPage.fxml", "Add Team", rootPane);
        AddTeamPageController addTeamController = AddTeamPageController.getInstance();
        addTeamController.loadSql("INSERT INTO team (name, project_id) VALUES (?, ?)");
        addTeamController.setProjectId(projectId);
    }

    @FXML
    private void deleteTeamButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        Team selectedRow = teamTable.getSelectionModel().getSelectedItem();
        int id = selectedRow.getId();

        String sql = "DELETE FROM team WHERE id = ?";

        alertConf.showDeleteConfirmationAlert(currentStage, sql, id);
        refreshTeamTable();
    }
    
}
