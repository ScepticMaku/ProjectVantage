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
        load();
    }    
    
    public static ProjectReportPDFController getInstance() {
        return instance;
    }
    
    public void load() {
        Platform.runLater(() -> {
            
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
    
    public void loadContent(int projectId) {
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
}
