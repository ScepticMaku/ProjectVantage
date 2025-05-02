/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.project_manager;

import projectvantage.utility.ReportPrinter;
import java.awt.print.PrinterJob;
import projectvantage.models.TeamMember;
import projectvantage.models.Task;
import projectvantage.models.Project;
import projectvantage.models.Team;
import projectvantage.utility.dbConnect;
import projectvantage.utility.AlertConfig;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.controllers.team_manager.TeamManagerPageController;
import projectvantage.controllers.team_manager.ViewTeamPageController;
import projectvantage.controllers.team_manager.AssignTeamPageController;
import projectvantage.controllers.task_manager.AddTaskPageController;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectvantage.controllers.task_manager.ViewTaskPageController;
import projectvantage.controllers.team_member.TeamMemberMainPageController;
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
    
    ObservableList<Task> taskList = FXCollections.observableArrayList();
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
    @FXML
    private Button viewTaskButton;
    @FXML
    private Button assignTeamButton;
    @FXML
    private Button addTaskButton;
    @FXML
    private Button deleteTaskButton;
    @FXML
    private TableColumn<Task, Integer> taskIdColumn;
    @FXML
    private TableColumn<Task, String> taskNameColumn;
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private ProgressBar taskProgressBar;
    @FXML
    private TableColumn<Task, String> statusColumn;
    @FXML
    private Button printReportButton;
    @FXML
    private Button seeTaskButton;
    @FXML
    private Button seeTeamButton;
    @FXML
    private ListView<?> recentActivityListView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        teamIdColumn.setSortable(false);
        teamNameColumn.setSortable(false);
        
        taskIdColumn.setSortable(false);
        taskNameColumn.setSortable(false);
        statusColumn.setSortable(false);
        
        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        load();
    }
    
    public void load() {
        Platform.runLater(() -> {
            refreshTeamTable();
            refreshTaskTable();
            
//            updateTaskProgress(getCompletedTasks());

            updateTaskProgress(getCompletedTasks());

            projectNameLabel.setText(projectName);
            descriptionText.setText(description);
            creationDateLabel.setText(creationDate);
            dueDateLabel.setText(dueDate);
            statusLabel.setText(status);
            creatorNameLabel.setText(creatorName);
            
            if(!(role.equals("admin") || role.equals("project manager"))) {
                addTaskButton.setVisible(false);
                deleteTaskButton.setVisible(false);
                assignTeamButton.setVisible(false);
                deleteTeamButton.setVisible(false);
                printReportButton.setVisible(false);
                
                seeTaskButton.setVisible(true);
                seeTeamButton.setVisible(true);
                
            }
        });
    }
    
    public static ViewProjectPageController getInstance() {
        return instance;
    }
    
    public void refreshTeamTable() {
        teamList.clear();
        loadTeamTable();
    }
    
    public void refreshTaskTable() {
        taskList.clear();
        loadTaskTable();
        updateTaskProgress(getCompletedTasks());
    }
    
    public void updateTaskProgress(int progress) {
        taskProgressBar.setProgress((double)progress/(double)getTotalTasks());
    }
    
    public void loadContent(int projectId, String username) {
        Project project = databaseConf.getProjectById(projectId);
        User user = databaseConf.getUserByUsername(username);
        
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
        
        User creator = databaseConf.getUserById(userId);
        
        if(creator != null) {
            this.creatorName = creator.getLastName();
        }
    }
    
    public int getCompletedTasks() {
        String sql = "SELECT COUNT(*) AS total FROM task WHERE status_id = 2 AND project_id = " + projectId;
        
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
    
    public int getTotalTasks() {
        String sql = "SELECT COUNT(*) AS total FROM task WHERE project_id = " + projectId;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    
    public void loadTaskTable() {
        String sql = "SELECT task.id, task.name, task.description, date_created, due_date, user.last_name, team_member_id, project_id, task_status.name AS status "
                + "FROM task INNER JOIN user ON user_id = user.id INNER JOIN task_status ON task.status_id = task_status.id "
                + "WHERE project_id = " + projectId + " ORDER BY task.id DESC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                taskList.add(new Task(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("date_created"),
                        result.getString("due_date"),
                        result.getString("last_name"),
                        result.getInt("team_member_id"),
                        result.getInt("project_id"),
                        result.getString("status")
                ));
            }
            
            taskTable.setItems(taskList);
            
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
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
            
            if(selectedTeam == null) {
                alertConf.showAlert(Alert.AlertType.ERROR, "Error Opening a Team", "You must select a team", currentStage);
                return;
            }
            
            int teamId = selectedTeam.getId();
            
            String viewTeamFXML = "/projectvantage/fxml/team_manager/ViewTeamPage.fxml";
            
            if(role.equals("admin")) {
                adminController.loadPage(viewTeamFXML, "Team");
                ViewTeamPageController.getInstance().loadContent(teamId, username);
                return;
            }

            if(role.equals("team manager")) {
                teamManagerController.loadPage(viewTeamFXML, "Team");
                ViewTeamPageController.getInstance().loadContent(selectedTeam.getId(), username);
                return;
            }
            
            if(role.equals("standard")) {
                TeamMemberMainPageController.getInstance().loadPage(viewTeamFXML, "Team");
                ViewTeamPageController.getInstance().loadContent(selectedTeam.getId(), username);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void addTeamButtonMouseClickHandler(MouseEvent event) throws Exception {
//        pageConf.loadWindow("/projectvantage/fxml/team_manager/AddTeamPage.fxml", "Add Team", rootPane);
//        AddTeamPageController addTeamController = AddTeamPageController.getInstance();
//    }
//
//    private void deleteTeamButtonMouseClickHandler(MouseEvent event) {
//        Stage currentStage = (Stage)rootPane.getScene().getWindow();
//        
//        Team selectedRow = teamTable.getSelectionModel().getSelectedItem();
//        int id = selectedRow.getId();
//
//        String sql = "DELETE FROM team WHERE id = ?";
//
//        alertConf.showDeleteConfirmationAlert(currentStage, sql, id);
//        refreshTeamTable();
//    }

    @FXML
    private void viewTaskButtonMouseClickHandler(MouseEvent event) throws Exception  {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        Task task = taskTable.getSelectionModel().getSelectedItem();
        
        if(task == null) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Error opening a Task", "You must select a task", currentStage);
            return;
        }
        
        int taskId = task.getId();
        
        pageConf.loadWindow("/projectvantage/fxml/task_manager/ViewTaskPage.fxml", "View Task", rootPane);
        ViewTaskPageController.getInstance().setUsername(username);
        ViewTaskPageController.getInstance().loadContent(taskId);
    }

    @FXML
    private void addTaskButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/task_manager/AddTaskPage.fxml", "Add Task", rootPane);
        AddTaskPageController addTaskController = AddTaskPageController.getInstance();
        addTaskController.setUsername(username);
        addTaskController.setProjectId(projectId);
    }


    @FXML
    private void assignTeamButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/team_manager/AssignTeamPage.fxml", "Assign Team", rootPane);
        AssignTeamPageController.getInstance().setProjectId(projectId);
    }

    @FXML
    private void removeTeamButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        int teamId = teamTable.getSelectionModel().getSelectedItem().getId();
        
        String sql = "UPDATE team SET project_id = NULL WHERE id = ?";
        
        if(db.executeQuery(sql, teamId)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Team successfully removed!", "Remove Successfull!", currentStage);
            refreshTeamTable();
        }
    }

    @FXML
    private void deleteTaskButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        String sql = "DELETE FROM task WHERE id = ?";
        
        int taskId = taskTable.getSelectionModel().getSelectedItem().getId();
        
        if(db.executeQuery(sql, taskId)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Task successfully deleted!", "Delete Successful!", currentStage);
            refreshTaskTable();
        }
    }

    @FXML
    private void printReportButtonMouseClickHandler(MouseEvent event) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new ReportPrinter());
        
        if(printerJob.printDialog()) {
             try {
                 printerJob.print();
             } catch (Exception e) {
                 e.printStackTrace();
             }
        }
    }

    @FXML
    private void seeTaskButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        TeamMember member = databaseConf.getTeamMemberByUserId(userId);
        int teamMemberId = member.getId();
        Task task = databaseConf.getTaskByTeamMemberId(teamMemberId);
        
        if(task == null) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Error Showing Task", "You are not assigned to a task yet.", currentStage);
            return;
        }
        
        int taskId = task.getId();
        pageConf.loadWindow("/projectvantage/fxml/task_manager/ViewTaskPage.fxml", "View Task", rootPane);
        ViewTaskPageController.getInstance().setUsername(username);
        ViewTaskPageController.getInstance().loadContent(taskId);
    }

    @FXML
    private void seeTeamButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        TeamMember member = databaseConf.getTeamMemberByUserId(userId);
        int teamId = member.getTeamId();
        Team team = databaseConf.getTeamById(teamId);
        
        if(team == null) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Error Showing Team", "You are not assigned to a team yet.", currentStage);
            return;
        }
        
        String viewTeamFXML = "/projectvantage/fxml/team_manager/ViewTeamPage.fxml";
        
        TeamMemberMainPageController.getInstance().loadPage(viewTeamFXML, "Team");
        ViewTeamPageController.getInstance().loadContent(teamId, username);
    }
}
