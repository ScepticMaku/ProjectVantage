/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_manager;

import projectvantage.utility.AlertConfig;
import projectvantage.utility.dbConnect;

import java.net.URL;
import java.util.ResourceBundle;
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
    dbConnect db = new dbConnect();

    private String sql;
    private int id = -1;
    
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
    }    
    
    public static AddTeamPageController getInstance() {
        return instance;
    }
    
    public void loadSql(String sql) {
        this.sql = sql;
    }
    
    public void setProjectId(int id) {
        this.id = id;
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
                alertConf.showAlert(Alert.AlertType.INFORMATION, "Team Successfully Added!", "Add Successful", currentStage);
                projectController.refreshTeamTable();
                currentStage.close();
            }
            return;
        }
        
        if(db.executeQuery(sql, name)) {
            System.out.println("Team added to database!");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Team Successfully Added!", "Add Successful", currentStage);
            teamController.refreshTable();
            currentStage.close();
        }
    }
}
