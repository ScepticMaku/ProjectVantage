/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.models.User;
import projectvantage.models.Project;
import projectvantage.models.Task;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.dbConnect;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author markj
 */
public class ProjectReportPDFController implements Initializable {

    private static ProjectReportPDFController instance;
    
    DatabaseConfig databaseConf = new DatabaseConfig();
    dbConnect db = new dbConnect();
    
    ObservableList<Task> taskList = FXCollections.observableArrayList();
    
    private int projectId;
    private String projectManagerName;
    private String projectName;
    private String description;
    private String dateCreated;
    private String dueDate;
    private String status;
    private int completedTasks;
    private int totalTasks;
    
    @FXML
    private Rectangle rectangle;
    @FXML
    private Label projectNameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label dateCreatedLabel;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label projectManagerLabel;
    @FXML
    private ProgressBar taskProgressBar;
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, String> taskNameColumn;
    @FXML
    private TableColumn<User, String> assignedToColumn;
    @FXML
    private TableColumn<Task, String> dueDateColumn;
    @FXML
    private TableColumn<Task, String> statusColumn;
    @FXML
    private Label completedTaskLabel;
    @FXML
    private Label totalTaskLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        taskNameColumn.setSortable(false);
        assignedToColumn.setSortable(false);
        dueDateColumn.setSortable(false);
        statusColumn.setSortable(false);
        
        taskNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        assignedToColumn.setCellValueFactory(new PropertyValueFactory("assignedLastName"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory("dueDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory("status"));
        
        load();
    }    
    
    public static ProjectReportPDFController getInstance() {
        return instance;
    }
    
    public void load() {
        Platform.runLater(() -> {
            refreshTaskTable();
            
            updateTaskProgress(completedTasks);
            
            projectNameLabel.setText(projectName);
            descriptionLabel.setText(description);
            dateCreatedLabel.setText(dateCreated);
            dueDateLabel.setText(dueDate);
            statusLabel.setText(status);
            projectManagerLabel.setText(projectManagerName);
            completedTaskLabel.setText(String.valueOf(completedTasks));
            totalTaskLabel.setText(String.valueOf(totalTasks));
        });
    }
    
    public void loadContent(int projectId, String username) {
        Project project = databaseConf.getProjectById(projectId);
        
        if(project != null) {
            this.projectId = projectId;
            this.projectName = project.getName();
            this.description = project.getDescription();
            this.dateCreated = project.getCreationDate();
            this.dueDate = project.getDueDate();
            this.status = project.getStatus();
            this.projectManagerName = project.getCreatorName();
            this.completedTasks = databaseConf.getCompletedTasks(projectId);
            this.totalTasks = databaseConf.getTotalTasks(projectId);
        }
    }
    
    public void updateTaskProgress(int progress) {
        taskProgressBar.setProgress((double)progress/(double)totalTasks);
    }
    
    public void refreshTaskTable() {
        taskList.clear();
        loadTaskTable();
    }
    
    public void loadTaskTable() {
        String sql = "SELECT task.id, task.name, task.description, date_created, due_date, user.last_name, team_member_id, member.last_name, project_id, task_status.name AS status "
                + "FROM task "
                + "INNER JOIN user ON user_id = user.id "
                + "INNER JOIN team_member ON task.team_member_id = team_member.id "
                + "INNER JOIN task_status ON task.status_id = task_status.id "
                + "INNER JOIN user member ON team_member.user_id = member.id "
                + "WHERE project_id = " + projectId + " ORDER BY task.id DESC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                Task task = new Task(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("date_created"),
                        result.getString("due_date"),
                        result.getString("user.last_name"),
                        result.getInt("team_member_id"),
                        result.getInt("project_id"),
                        result.getString("status")
                );
                task.setAssignedLastName(result.getString("member.last_name"));
                taskList.add(task);
            }
            taskTable.setItems(taskList);
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
