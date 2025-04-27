/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.task_manager;

import projectvantage.models.Project;
import projectvantage.models.User;
import projectvantage.models.TeamMember;
import projectvantage.models.Task;
import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.controllers.task_manager.AssignTaskPageController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.controllers.project_manager.ProjectManagerPageController;
import projectvantage.controllers.project_manager.ViewProjectPageController;
import projectvantage.controllers.team_member.ViewTeamMemberPageController;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class ViewTaskPageController implements Initializable {
    
    private static ViewTaskPageController instance;
    
    AlertConfig alertConf = new AlertConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    PageConfig pageConf = new PageConfig();
    
    private int taskId;
    private int userId;
    private int projectId;
    private int teamMemberId;
    private String taskName;
    private String description;
    private String dateCreated;
    private String dueDate;
    private String createdBy;
    private String assignedTo;
    private String projectName;
    private String status;
    private String username;
    private String role;
    
    @FXML
    private Label taskNameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Label createdByLabel;
    @FXML
    private Label assignedToLabel;
    @FXML
    private Label fromProjectLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button viewProjectButton;
    @FXML
    private Button viewMemberButton;
    @FXML
    private Button editTaskButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button completeTaskButton;
    @FXML
    private Button uncompleteTaskButton;
    @FXML
    private Button assignMemberButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
           taskNameLabel.setText(taskName);
           descriptionLabel.setText(description);
           dateLabel.setText(dateCreated);
           dueDateLabel.setText(dueDate);
           createdByLabel.setText(createdBy);
           assignedToLabel.setText(assignedTo);
           fromProjectLabel.setText(projectName);
           statusLabel.setText(status);
           
           if(status.equals("completed")) {
               uncompleteTaskButton.setVisible(true);
               completeTaskButton.setVisible(false);
           } else {
               uncompleteTaskButton.setVisible(false);
               completeTaskButton.setVisible(true);
           }
           
        });
    }
    
    public static ViewTaskPageController getInstance() {
        return instance;
    }
    
    public void setUsername(String username) {
        this.username = username;
        
        User user = databaseConf.getUserByUsername(username);
        
        this.role = user.getRole();
    }

    public void loadContent(int taskId) {
        this.taskId = taskId;
        
        Task task = databaseConf.getTaskById(taskId);
        
        this.taskName = task.getName();
        this.description = task.getDescription();
        this.dateCreated = task.getCreationDate();
        this.dueDate = task.getDueDate();
        this.createdBy = task.getCreatorName();
        this.teamMemberId = task.getTeamMemberId();
        this.projectId = task.getProjectId();
        this.status = task.getStatus();
        
        Project project = databaseConf.getProjectById(projectId);
        
        this.projectName = project.getName();
        
        TeamMember teamMember = databaseConf.getTeamMemberById(teamMemberId);
        
        if(teamMember == null) {
            assignMemberButton.setVisible(true);
            viewMemberButton.setVisible(false);
            return;
        }
            
        assignMemberButton.setVisible(false);
        viewMemberButton.setVisible(true);
        
        this.assignedTo = teamMember.getLastName();
    }
    
    @FXML
    private void viewProjectButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        AdminPageController adminController = AdminPageController.getInstance();
        ProjectManagerPageController projectManagerController = ProjectManagerPageController.getInstance();
        
        String viewProjectFXML = "/projectvantage/fxml/project_manager/ViewProjectPage.fxml";
        
        try {
            if(role.equals("admin")) {
                adminController.loadPage(viewProjectFXML, projectName);
                ViewProjectPageController.getInstance().loadContent(projectId, username);
                currentStage.close();
                return;
            }
            
            projectManagerController.loadPage(viewProjectFXML, projectName);
            currentStage.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            alertConf.showAlert(Alert.AlertType.ERROR, "Error Opening a Project", "You must select a project", currentStage);
        }
    }

    @FXML
    private void viewMemberButtonMouseClickHandler(MouseEvent event) throws Exception{
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        String viewTeamMemberFXML = "/projectvantage/fxml/team_member/ViewTeamMemberPage.fxml";
        
        int id = databaseConf.getUserIdById(teamMemberId);
        
        if(assignedTo == null) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Error Viewing Team Member", "A team member is not assigned yet.", currentStage);
            return;
        }
        
        pageConf.loadWindow(viewTeamMemberFXML, "Team Member", rootPane);
        ViewTeamMemberPageController.getInstance().loadContent(id);
    }

    @FXML
    private void editTaskButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void completeTaskButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void uncompleteTaskButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void assignMemberButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/task_manager/AssignTaskPage.fxml", "Assign Task", rootPane);
        AssignTaskPageController.getInstance().setProjectId(projectId);
        AssignTaskPageController.getInstance().setTaskId(taskId);
    }
    
}
