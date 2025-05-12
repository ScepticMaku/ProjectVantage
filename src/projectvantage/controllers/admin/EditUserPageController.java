/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import java.io.File;
import projectvantage.utility.LogConfig;
import projectvantage.utility.SessionConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.Config;
import projectvantage.utility.dbConnect;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;
import projectvantage.models.Role;
import projectvantage.utility.ElementConfig;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projectvantage.models.ProjectStatus;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class EditUserPageController implements Initializable {
    
    private static EditUserPageController instance;
    
    dbConnect db = new dbConnect();
    LogConfig logConf = new LogConfig();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    Config config = new Config();
    DatabaseConfig dbConf = new DatabaseConfig();
    ElementConfig elementConf = new ElementConfig();
    
    ObservableList<Role> roleList = FXCollections.observableArrayList();
    
    private static final String TARGET_DIRECTORY = "pfp/";
    private static final double IMAGE_SIZE = 100;
    
    private File selectedFile;
    
    private int userId;
    private int sessionUserId;
    private int userRoleId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String role;
    private String status;
    private String username;

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
    private Button deactivateButton;
    @FXML
    private Button activateButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button submitUserButton;
    @FXML
    private Button fileChooseButton;
    @FXML
    private ComboBox<Role> roleComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            SessionConfig sessionConf = SessionConfig.getInstance();
            
            sessionUserId = sessionConf.getId();
            
            roleComboBox.setPromptText(role);
        });
        
        loadColumnData();
    }
    
    public static EditUserPageController getInstance() {
        return instance;
    }
    
    public void loadUserContents(String userInput) {
        
        User user = dbConf.getUserByUsername(userInput);
        
        userId = user.getId();
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        lastName = user.getLastName();
        emailAddress = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        username = user.getUsername();
        role = user.getRole();
        status = user.getStatus();
        
        firstNameField.setText(firstName);
        middleNameField.setText(middleName);
        lastNameField.setText(lastName);
        emailAddressField.setText(emailAddress);
        phoneNumberField.setText(phoneNumber);
        usernameLabel.setText(username);
        usernameField.setText(username);
        
        boolean isActive = status.equals("active");
        
        if(isActive) {
            activateButton.setVisible(false);
        }
        
        if(!isActive) {
            deactivateButton.setVisible(false);
        }
    }
    
    private String getFileExtension(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    private void moveAndSaveImageToDatabase() throws Exception {
        File targetDir = new File(TARGET_DIRECTORY);
        
        if(selectedFile == null) {
            return;
        }
        
        String newFileName = "user_" + username + getFileExtension(selectedFile);
        File targetFile = new File(targetDir, newFileName);
        
        Files.copy(selectedFile.toPath().toAbsolutePath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        String sql;
        String imagePath = targetFile.getPath();
        
        if(dbConf.getImagePath(username) == null) {
            sql = "INSERT INTO user_image (user_id, image_path) VALUES (?, ?)";
            db.executeQuery(sql, userId, imagePath);
            logConf.logEditUserProfilePicture(sessionUserId, username);
            System.out.println("User Image updated successfully!");
            return;
        }
        
        sql = "UPDATE user_image SET image_path = ? WHERE user_id = ?";
        db.executeQuery(sql, imagePath, userId);
        logConf.logEditUserProfilePicture(sessionUserId, username);
        System.out.println("User Image updated successfully!");
    }
    
    private void loadColumnData() {
            String sql = "SELECT id, name FROM user_role WHERE id NOT IN (1)";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                roleList.add(new Role(
                        result.getInt("id"),
                        result.getString("name")
                ));
            }
            displayRoles(roleComboBox, roleList);
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private boolean isUsernameDuplicated(String column, String value) throws SQLException {
        
        try(ResultSet result = db.getData("SELECT " + column + " FROM user WHERE NOT username = '" + username + "'")) {    
            while(result.next()) {
                if(value.equals(result.getString(column)))
                    return true;
            }
        }
        return false;
    }
    
    private boolean isEmailDuplicated(String column, String value) throws SQLException {
        
        try(ResultSet result = db.getData("SELECT " + column + " FROM user WHERE NOT email = '" + emailAddress + "'")) {    
            while(result.next()) {
                if(value.equals(result.getString(column)))
                    return true;
            }
        }
        return false;
    }
    
    private boolean verifyInput(Stage currentStage, String firstName, String lastName, String phoneNumber, String emailAddress, String user) throws Exception {
        if(firstName.isEmpty()) {
            alertConf.showEditEUserErrorAlert(currentStage, "First name field must not be empty.");
            return true;
        }
        
        if(lastName.isEmpty()) {
            alertConf.showEditEUserErrorAlert(currentStage, "Last name field must not be empty.");
            return true;
        }
        
        if(emailAddress.isEmpty()) {
            alertConf.showEditEUserErrorAlert(currentStage, "Email address field must not be empty.");
            return true;
        }
        
        if(phoneNumber.isEmpty()) {
            alertConf.showEditEUserErrorAlert(currentStage, "Phone number field must not be empty.");
            return true;
        }
        
        if(username.isEmpty()) {
            alertConf.showEditEUserErrorAlert(currentStage, "Username field must not be empty.");
            return true;
        }
        
        if(!config.isValidEmailFormat(emailAddress)) {
            alertConf.showEditEUserErrorAlert(currentStage, "Email format is invalid.");
            return true;
        }
        
        if(config.isValidPhoneNumber(phoneNumber)) {
            alertConf.showEditEUserErrorAlert(currentStage, "Phone number is invalid.");
            return true;
        }
        
        if(!config.isValidPhoneNumberFormat(phoneNumber)) {
            alertConf.showEditEUserErrorAlert(currentStage, "Phone number is invalid.");
            return true;
        }
        
        if(isUsernameDuplicated("username", user)) {
            alertConf.showEditEUserErrorAlert(currentStage, "Username already exists.");
            return true;
        }
        
        if(isEmailDuplicated("email", emailAddress)) {
            alertConf.showEditEUserErrorAlert(currentStage, "Username already exists.");
            return true;
        }
        return false;
    }
    
    private boolean updateUserStatus(String query) {
        if(db.executeQuery(query)) {
            System.out.println("User status updated!");
            return true;
        }
        return false;
    }
    
    private void refreshPage() throws Exception {
        EditUserPageController.getInstance().loadUserContents(username);
    }
            
    private void backButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }



    @FXML
    private void deactivateButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        String sql = "UPDATE user SET status_id = 1 WHERE username = '" + username + "'";
        
        if(!updateUserStatus(sql)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "User Deactivation Failed", "There was a problem deactivating the user.", currentStage);
            return;
        }
        
        alertConf.showAlert(Alert.AlertType.INFORMATION, "User Deactivation Successful", "User successfully deactivated!", currentStage);
        AdminUserPageController.getInstance().loadUser(username);
        logConf.logDeactivateUser(sessionUserId, username);
        refreshPage();
    }

    @FXML
    private void activateButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        String sql = "UPDATE user SET status_id = 2 WHERE username = '" + username + "'";
        
        if(!updateUserStatus(sql)) {
            alertConf.showAlert(Alert.AlertType.INFORMATION, "User Activation Failed", "There was a problem activating the user.", currentStage);
            return;
        }
        
        alertConf.showAlert(Alert.AlertType.INFORMATION, "User Activation", "User successfully activated!", currentStage);
        logConf.logActivateUser(sessionUserId, username);
        AdminUserPageController.getInstance().loadUser(username);
        refreshPage();
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
    private void submitUserButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        
        AdminUserPageController adminUserController = AdminUserPageController.getInstance();
        
        int id;
        
        String fName = firstNameField.getText();
        String mName = middleNameField.getText();
        String lName = lastNameField.getText();
        String pNumber = phoneNumberField.getText();
        String eAddress = emailAddressField.getText();
        String uName = usernameField.getText();
        
        String sql = "UPDATE user SET first_name = ?, middle_name = ?, last_name = ?, phone_number = ?, email = ?, username = ?, role_id = ? WHERE username = ?";
        
        if(!verifyInput(currentStage, fName, lName, pNumber, eAddress, uName)) {
            Role selectedRole = roleComboBox.getSelectionModel().getSelectedItem();
            
            id = dbConf.getUserRoleIdByUsername(username);
            
            if(selectedRole != null) {
               id = selectedRole.getId();
            }
            
            if(db.executeQuery(sql, fName, mName, lName, pNumber, eAddress, uName, id, username)) {
                System.out.println("User updated successfully!");
                
                StringBuilder description  = new StringBuilder("Updated user information, changed:");
                
                if(!fName.equals(firstName)) {
                    description.append(" | First Name: ").append(fName);
                }
                
                if(!mName.equals(middleName)) {
                    description.append(" | Middle Name: ").append(mName);
                }
                
                if(!lName.equals(lastName)) {
                    description.append(" | Last Name: ").append(lName);
                }
                
                if(!pNumber.equals(phoneNumber)) {
                    description.append("| Phone Number: ").append(pNumber);
                }
                
                if(!eAddress.equals(emailAddress)) {
                    description.append("| Email Address: ").append(eAddress);
                }
                
                if(!uName.equals(username)) {
                    description.append("| Username: ").append(uName);
                }
                
                if(id != dbConf.getUserRoleIdByUsername(username)) {
                    description.append(" | Role ID: ").append(id);
                }
                
                logConf.logEditUser(sessionUserId, description.toString());
                alertConf.showAlert(Alert.AlertType.INFORMATION, "User Update Successful", "User Updated Succesfully!", currentStage);
                moveAndSaveImageToDatabase();
                elementConf.loadProfilePicture(username, adminUserController.getUserPhoto(), IMAGE_SIZE);
                adminUserController.loadUser(username);
                currentStage.close();
            }
        }
    }

    @FXML
    private void fileChooseButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Photo");
        
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        
        selectedFile = fileChooser.showOpenDialog(currentStage);
        
        if(selectedFile == null) {
            alertConf.showAlert(Alert.AlertType.ERROR, "File Selection Failed", "File selection cancelled.", currentStage);
        }
    }
}
