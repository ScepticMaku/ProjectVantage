/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_manager;

import projectvantage.utility.AlertConfig;
import projectvantage.utility.SessionConfig;
import projectvantage.utility.LogConfig;
import projectvantage.utility.dbConnect;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectvantage.controllers.project_manager.ViewProjectPageController;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class AddTeamPageController implements Initializable {
    
    private static AddTeamPageController instance;
    
    AlertConfig alertConf = new AlertConfig();
    
    LogConfig logConf = new LogConfig();
    dbConnect db = new dbConnect();

    private String sql;
    private int id = -1;
    private int userId;
    
    @FXML
    private Button addButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField teamNameField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            SessionConfig sessionConf = SessionConfig.getInstance();
            
            userId = sessionConf.getId();
            
        });
    }    
    
    public static AddTeamPageController getInstance() {
        return instance;
    }

    @FXML
    private void addButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        ViewProjectPageController projectController = ViewProjectPageController.getInstance();
        TeamPageController teamController = TeamPageController.getInstance();
        
        String name = teamNameField.getText();
        
        if(name.isEmpty()) {
            alertConf.showAddTeamErrorAlert(currentStage, "Team name must not be empty");
            return;
        }
        
        if(id != -1) {
                if(db.executeQuery(sql, name, id)) {
                System.out.println("Team added to database!");
                logConf.logAddTeam(userId, id, name);
                alertConf.showAlert(Alert.AlertType.INFORMATION, "Team Successfully Added!", "Add Successful", currentStage);
                projectController.refreshTeamTable();
                currentStage.close();
            }
            return;
        }
        
        this.sql = "INSERT INTO team (name) VALUES (?)";
        
        if(db.executeQuery(sql, name)) {
            System.out.println("Team added to database!");
            logConf.logAddTeam(userId, id, name);
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Team Successfully Added!", "Add Successful", currentStage);
            teamController.refreshTable();
            currentStage.close();
        }
    }
}
