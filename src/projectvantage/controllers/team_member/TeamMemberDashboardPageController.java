/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_member;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class TeamMemberDashboardPageController implements Initializable {
    
    private static TeamMemberDashboardPageController instance;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label assignedTasksLabel;
    @FXML
    private Label tasksDueLabel;
    @FXML
    private Label completedTasksLabel;
    @FXML
    private TableView<?> tasksTable;
    @FXML
    private TableColumn<?, ?> taskNameColumn;
    @FXML
    private TableColumn<?, ?> projectColumn;
    @FXML
    private TableColumn<?, ?> deadlineColumn;
    @FXML
    private TableColumn<?, ?> priorityColumn;
    @FXML
    private TableColumn<?, ?> statusColumn;
    @FXML
    private TableView<?> activityTable;
    @FXML
    private TableColumn<?, ?> dateColumn;
    @FXML
    private TableColumn<?, ?> taskColumn;
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
    
    public static TeamMemberDashboardPageController getInstance() {
        return instance;
    }
    
    public void loadContent(String user) {
        usernameLabel.setText(user);
    }
}
