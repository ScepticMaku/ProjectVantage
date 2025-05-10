/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.utility.LogConfig;
import projectvantage.models.User;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.Role;
import projectvantage.controllers.authentication.RegisterController;
import projectvantage.utility.dbConnect;
import projectvantage.utility.Config;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.AuthenticationConfig;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class AddUserPageController implements Initializable {
    
    RegisterController registerController = new RegisterController();
    dbConnect db = new dbConnect();
    Config config = new Config();
    AuthenticationConfig authConf = new AuthenticationConfig();
    AlertConfig alertConf = new AlertConfig();
    LogConfig logConf = new LogConfig();
    
    ObservableList<Role> roleList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailAddressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private Button addUserButton;
    @FXML
    private ComboBox<Role> roleComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        loadColumnData();
    }
    
    private void insertUser(Stage currentStage, String query, String firstName, String middleName, String lastName, String emailAddress, String phoneNumber, String username, String salt, String password, int id) {
        if(db.executeQuery(query, firstName, middleName, lastName, emailAddress, phoneNumber, username, salt, password, id)) {
            System.out.println("User added to database!");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "User successfully registered!", "Register Successful", currentStage);
        }
    }
    
    private void loadColumnData() {
            String sql = "SELECT id, name FROM user_role WHERE id NOT IN (1)";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                roleList.add(new Role(
                        result.getInt("id"),
                        result.getString("name")
                ));
                displayRoles(roleComboBox, roleList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void displayRoles(ComboBox<Role> comboBox, ObservableList<Role> list) {
        comboBox.setItems(list);

        comboBox.setCellFactory(lv -> new ListCell<Role>(){
                @Override
                protected void updateItem(Role item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            });

        comboBox.setButtonCell(new ListCell<Role>() {
            @Override
            protected void updateItem(Role item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    @FXML
    private void addUserButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        
        UserManagementPageController userController = UserManagementPageController.getInstance();
        
        String firstName = firstNameField.getText();
        String middleName = middleNameField.getText();
        String lastName = lastNameField.getText();
        String emailAddress = emailAddressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String passwordConfirm = confirmPasswordField.getText();
        
        String query = "INSERT INTO user (first_name, middle_name, last_name, email, phone_number, username, salt, password, secret_key, role_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, '', ?)";
        
        if(registerController.verifyUser(currentStage, firstName, lastName, emailAddress, phoneNumber, username, password, passwordConfirm))
            return;
        
        Role selectedRole = roleComboBox.getSelectionModel().getSelectedItem();
        
        if(selectedRole == null) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Failed to Add User", "You must select a row.", currentStage);
            return;
        }
        
        int id = selectedRole.getId();
        
        String salt = authConf.generateSalt();
        String hashedPassword = authConf.hashPassword(password, salt);

        insertUser(currentStage, query, firstName, middleName, lastName, emailAddress, phoneNumber, username, salt, hashedPassword, id);
        currentStage.close();
        userController.refreshTable();
    }
}
