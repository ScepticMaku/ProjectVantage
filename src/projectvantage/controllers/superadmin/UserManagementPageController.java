/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.superadmin;

import projectvantage.models.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import projectvantage.utility.dbConnect;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class UserManagementPageController implements Initializable {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> userId;
    @FXML
    private TableColumn<User, String> userFirstName;
    @FXML
    private TableColumn<User, String> userLastName;
    @FXML
    private TableColumn<User, String> userRole;
    @FXML
    private TableColumn<User, String> userStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        userStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        loadTableData();
    }    
    
    private void loadTableData() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        dbConnect db = new dbConnect();
        String sql = "SELECT id, first_name, middle_name, last_name, email, phone_number, username, password, role, status FROM user";
        
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
                        result.getString("password"),
                        result.getString("role"),
                        result.getString("status")
                ));
            }
            userTable.setItems(userList);
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
    
}
