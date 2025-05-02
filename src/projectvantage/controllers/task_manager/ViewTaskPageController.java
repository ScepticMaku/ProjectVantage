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
import projectvantage.utility.dbConnect;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.controllers.project_manager.ProjectManagerPageController;
import projectvantage.controllers.project_manager.ViewProjectPageController;
import projectvantage.controllers.team_member.ViewTeamMemberPageController;
import projectvantage.controllers.task_manager.TaskPageController;
import projectvantage.controllers.team_member.TeamMemberMainPageController;

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
    dbConnect db = new dbConnect();
    
    private int taskId;
    private int userId;
    private int projectId; 
    private int guestTeamMemberId;
    private int teamMemberId;
    private String guestRole;
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
    @FXML
    private TextField taskNameField;
    @FXML
    private TextArea taskDescriptionTextArea;
    @FXML
    private Button cancelButton;
    @FXML
    private Button applyButton;
    @FXML
    private Button removeMemberButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        load();
    }
    
    public void load() {
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
           
           if(guestTeamMemberId != teamMemberId && !role.equals(("admin"))) {
               completeTaskButton.setVisible(false);
               uncompleteTaskButton.setVisible(false);
           }
           
           if(!(role.equals("admin") || role.equals("project manager"))) {
               editTaskButton.setVisible(false);
               removeMemberButton.setVisible(false);
           }
           
           Stage currentStage = (Stage)rootPane.getScene().getWindow();
           
           currentStage.setOnCloseRequest(event -> {
               ViewProjectPageController projectController = ViewProjectPageController.getInstance();
               TaskPageController taskController = TaskPageController.getInstance();
               
               if(projectController != null) {
                    projectController.load();
                    projectController.loadContent(projectId, username);
                    return;
               }
               
               if(taskController != null) {
                   taskController.refreshTaskTable();
               }
           });
           
        });
    }
    
    public static ViewTaskPageController getInstance() {
        return instance;
    }
    
    public void setUsername(String username) {
        this.username = username;
        
        User user = databaseConf.getUserByUsername(username);
        
        this.role = user.getRole();
        this.userId = user.getId();
        
        TeamMember member = databaseConf.getTeamMemberByUserId(userId);
        
        if(member != null) {
            this.guestTeamMemberId = member.getId();
            this.guestRole = member.getRole();
        }
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
        
        if(teamMember != null) {
            assignMemberButton.setVisible(false);
            viewMemberButton.setVisible(true);
            removeMemberButton.setVisible(true);

            this.assignedTo = teamMember.getLastName();
            return;
        }
        
        if(role.equals("admin") || role.equals("project manager") || guestRole.equals("team leader")) {
            assignMemberButton.setVisible(true);
            viewMemberButton.setVisible(false);
            removeMemberButton.setVisible(false);
            return;
        }
        
        assignMemberButton.setVisible(false);
    }
    
    @FXML
    private void viewProjectButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        AdminPageController adminController = AdminPageController.getInstance();
        ProjectManagerPageController projectManagerController = ProjectManagerPageController.getInstance();
        TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
        
        String viewProjectFXML = "/projectvantage/fxml/project_manager/ViewProjectPage.fxml";
        
        try {
            switch(role) {
                case "admin":
                    adminController.loadPage(viewProjectFXML, projectName);
                    ViewProjectPageController.getInstance().loadContent(projectId, username);
                    currentStage.close();
                break;
                case "standard":
                    teamMemberController.loadPage(viewProjectFXML, projectName);
                    ViewProjectPageController.getInstance().loadContent(projectId, username);
                    currentStage.close();
                break;
            }
            
//            projectManagerController.loadPage(viewProjectFXML, projectName);
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
        taskNameLabel.setVisible(false);
        descriptionLabel.setVisible(false);
        completeTaskButton.setVisible(false);
        uncompleteTaskButton.setVisible(false);
        editTaskButton.setVisible(false);
        
        cancelButton.setVisible(true);
        applyButton.setVisible(true);
        taskNameField.setVisible(true);
        taskDescriptionTextArea.setVisible(true);
        taskNameField.setText(taskNameLabel.getText());
        taskDescriptionTextArea.setText(descriptionLabel.getText());
        
    }

    @FXML
    private void completeTaskButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String sql = "UPDATE task SET status_id = 2 WHERE id = " + taskId;
        
        if(db.executeQuery(sql)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Task Completion Successful!", "Task completed!", currentStage);
            loadContent(taskId);
            load();
        }
    }

    @FXML
    private void uncompleteTaskButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String sql = "UPDATE task SET status_id = 1 WHERE id = " + taskId;
        
        if(db.executeQuery(sql)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Task Uncompletion Successful!", "Task completion revoked!", currentStage);
            loadContent(taskId);
            load();
        }
    }

    @FXML
    private void assignMemberButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/task_manager/AssignTeamMemberPage.fxml", "Assign Team", rootPane);
        AssignTeamMemberPageController.getInstance().setProjectId(projectId);
        AssignTeamMemberPageController.getInstance().setTaskId(taskId);
    }

    @FXML
    private void cancelButtonMouseClickHandler(MouseEvent event) {
        taskNameLabel.setVisible(true);
        descriptionLabel.setVisible(true);
        completeTaskButton.setVisible(true);
        uncompleteTaskButton.setVisible(true);
        editTaskButton.setVisible(true);
        
        cancelButton.setVisible(false);
        applyButton.setVisible(false);
        taskNameField.setVisible(false);
        taskDescriptionTextArea.setVisible(false);
    }

    @FXML
    private void applyButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        taskNameLabel.setVisible(true);
        descriptionLabel.setVisible(true);
        completeTaskButton.setVisible(true);
        uncompleteTaskButton.setVisible(true);
        editTaskButton.setVisible(true);
        
        cancelButton.setVisible(false);
        applyButton.setVisible(false);
        taskNameField.setVisible(false);
        taskDescriptionTextArea.setVisible(false);
        
        String sql = "UPDATE task SET name = ?, description = ? WHERE id = ?";
        String name = taskNameField.getText();
        String desc = taskDescriptionTextArea.getText();
        
        if(db.executeQuery(sql, name, desc, taskId)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Edit Successful", "Task successfully edited!", currentStage);
            loadContent(taskId);
            load();
        }
    }

    @FXML
    private void removeMemberButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String sql = "UPDATE task SET team_member_id = NULL WHERE id = ?";
        
        if(db.executeQuery(sql, taskId)) {
            System.out.println("Task updated successfully!|");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Member Successfully Removed!", "Remove success!", currentStage);
            currentStage.close();
            ViewTaskPageController.getInstance().loadContent(taskId);
            ViewTaskPageController.getInstance().load();
        }
    }
    
}
