/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.models.Project;
import projectvantage.models.User;
import projectvantage.utility.DatabaseConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author markj
 */
public class ProjectReportPDFController implements Initializable {

    private static ProjectReportPDFController instance;
    
    DatabaseConfig databaseConf = new DatabaseConfig();
    
    private int projectId;
    private String projectManagerName;
    private String projectName;
    private String description;
    private String dateCreated;
    private String dueDate;
    private String status;
    
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
            projectNameLabel.setText(projectName);
            descriptionLabel.setText(description);
            dateCreatedLabel.setText(dateCreated);
            dueDateLabel.setText(dueDate);
            statusLabel.setText(status);
            projectManagerLabel.setText(projectManagerName);
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
        }
    }
    
}
