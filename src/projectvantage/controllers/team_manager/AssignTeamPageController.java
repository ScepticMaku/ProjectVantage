/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_manager;

import projectvantage.models.Team;
import projectvantage.controllers.project_manager.ViewProjectPageController;
import projectvantage.utility.dbConnect;
import projectvantage.utility.AlertConfig;

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
public class AssignTeamPageController implements Initializable {
    
    private static AssignTeamPageController instance;
    
    AlertConfig alertConf = new AlertConfig();
    dbConnect db = new dbConnect();
    
    private int projectId;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Team> teamTable;
    @FXML
    private TableColumn<Team, String> teamNameColumn;
    @FXML
    private Button assignButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        teamNameColumn.setSortable(false);
        
        teamNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        
        Platform.runLater(() -> {
            loadTeams();
        });
    }    
    
    public static AssignTeamPageController getInstance() {
        return instance;
    }
    
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    
    private void loadTeams() {
        String sql = "SELECT id, name, project_id FROM team WHERE project_id IS NULL ORDER BY id DESC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                teamList.add(new Team(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("project_id")
                ));
                
                teamTable.setItems(teamList);
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    ObservableList<Team> teamList = FXCollections.observableArrayList();

    @FXML
    private void assignButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        Team team = teamTable.getSelectionModel().getSelectedItem();
        
        int teamId = team.getId();
        
        String sql = "UPDATE team SET project_id = ? WHERE id = ?";
        
        if(db.executeQuery(sql, projectId, teamId)) {
            System.out.println("Team updated successfully!");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Team Successfully Assigned!", "Assign Success!", currentStage);
            currentStage.close();
            ViewProjectPageController.getInstance().refreshTeamTable();
        }
    }
    
}
