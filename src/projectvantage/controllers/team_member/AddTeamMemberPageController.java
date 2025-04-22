/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_member;

import projectvantage.controllers.team_manager.ViewTeamPageController;
import projectvantage.utility.dbConnect;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.TeamMember;
import projectvantage.utility.AlertConfig;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projectvantage.models.User;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class AddTeamMemberPageController implements Initializable {

    private static AddTeamMemberPageController instance;
    
    DatabaseConfig databaseConf = new DatabaseConfig();
    AlertConfig alertConf = new AlertConfig();
    dbConnect db = new dbConnect();
    
    private static final int CARD_SPACING = 5;
    private static final int INSET_VALUE = 10;
    
    ObservableList<User> userList = FXCollections.observableArrayList();
    
    private int teamId;
    
    @FXML
    private Button addButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableView<User> userTable;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        lastNameColumn.setSortable(false);
        usernameColumn.setSortable(false);
        
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        
        Platform.runLater(() -> {
            loadUsers();
        });
    }
    
    public static AddTeamMemberPageController getInstance() {
        return instance;
    }
    
    public void loadContent(int teamId) {
        this.teamId = teamId;
    }
    
    public void refreshTable() {
        userList.clear();
        loadUsers();
    }
    
    private void loadUsers() {
        String sql = "SELECT user.id, first_name, middle_name, last_name, email, phone_number, username, salt, password, secret_key, user_role.name AS role, user_status.name AS status "
                + "FROM user INNER JOIN user_role ON user.role_id = user_role.id INNER JOIN user_status ON user.status_id = user_status.id WHERE user.role_id = 2 ORDER BY user.id ASC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                userList.add(new User(
                        result.getInt("id"),
                        result.getString("first_name"),
                        result.getString("middle_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("phone_number"),
                        result.getString("username"),
                        result.getString("salt"),
                        result.getString("password"),
                        result.getString("secret_key"),
                        result.getString("role"),
                        result.getString("status")
                ));
            }
            
            userTable.setItems(userList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database Error: " + e.getMessage());
        }
    }
    
    private boolean isUserAlreadyAdded(int id) {
        String sql = "SELECT user_id FROM team_member WHERE user_id = " + id;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database Error: " + e.getMessage());
        }
        return false;
    }

    @FXML
    private void addButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        User user = userTable.getSelectionModel().getSelectedItem();
        
        int id = user.getId();
//        String username = user.getUsername();
        
        String sql = "INSERT INTO team_member (team_id, user_id) "
                + "VALUES (?, ?)";
        
        if(isUserAlreadyAdded(id)) {
            alertConf.showAddTeamErrorAlert(currentStage, "user is already in a team");
            return;
        }
        
        if(db.executeQuery(sql, teamId, id)) {
            System.out.println("Team added to database!");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Team member successfully added!", "Team member add successful!", currentStage);
            currentStage.close();
            ViewTeamPageController.getInstance().refreshTable();
        }
    }
    
}
