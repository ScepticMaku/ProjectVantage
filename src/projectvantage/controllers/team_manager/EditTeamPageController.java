/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_manager;

import projectvantage.models.Team;
import projectvantage.utility.SessionConfig;
import projectvantage.utility.LogConfig;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class EditTeamPageController implements Initializable {
    
    private static EditTeamPageController instance;
    
    LogConfig logConf = new LogConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    AlertConfig alertConf = new AlertConfig();
    dbConnect db = new dbConnect();
    
    private int id;
    private int userId;
    private String name;
    
    @FXML
    private TextField teamNameField;
    @FXML
    private Button submitButton;
    @FXML
    private AnchorPane rootPane;
    
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
            
            teamNameField.setText(name);
        });
    }    
    
    public static EditTeamPageController getInstance() {
        return instance;
    }
    
    public void loadContent(int id) {
        Team team = databaseConf.getTeamById(id);
        
        this.id = team.getId();
        this.name = team.getName();
    }

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String nameField = teamNameField.getText();
        
        if(nameField.isEmpty()) {
            alertConf.showEditTeamErrorAlert(currentStage, "Team name must not be empty.");
            return;
        }
        
        String sql = "UPDATE team SET name = ? WHERE id = ?";
        
        if(db.executeQuery(sql, nameField, id)) {
            System.out.println("Team updated successfully!");
            logConf.logEditTeam(userId, id, nameField);
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Team Update Successful", "Team Updated Successfully!", currentStage);
            TeamPageController.getInstance().refreshTable();
            currentStage.close();
        }
    }
    
}
