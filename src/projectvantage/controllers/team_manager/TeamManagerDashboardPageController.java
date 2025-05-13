/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_manager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class TeamManagerDashboardPageController implements Initializable {
    
    private static TeamManagerDashboardPageController instance;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label teamMembersLabel;
    @FXML
    private Label activeTasksLabel;
    @FXML
    private Label completedTasksLabel;
    @FXML
    private TableView<?> teamPerformanceTable;
    @FXML
    private TableColumn<?, ?> memberNameColumn;
    @FXML
    private TableColumn<?, ?> tasksAssignedColumn;
    @FXML
    private TableColumn<?, ?> tasksCompletedColumn;
    @FXML
    private TableColumn<?, ?> performanceColumn;
    @FXML
    private TableColumn<?, ?> statusColumn;
    @FXML
    private TableView<?> activityTable;
    @FXML
    private TableColumn<?, ?> dateColumn;
    @FXML
    private TableColumn<?, ?> memberColumn;
    @FXML
    private TableColumn<?, ?> actionColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }    
    
    public static TeamManagerDashboardPageController getInstance() {
        return instance;
    }
    
    public void loadContent(String user) {
        usernameLabel.setText(user);
    }
    
}
