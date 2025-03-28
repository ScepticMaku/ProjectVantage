/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import java.io.File;
import java.io.IOException;
import projectvantage.utility.PageConfig;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.Config;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.controllers.team_member.TeamMemberMainPageController;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class EditProfilePageController implements Initializable {
    
    private static EditProfilePageController instance;
    
    dbConnect db = new dbConnect();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    Config config = new Config();
    DatabaseConfig dbConf = new DatabaseConfig();
    ElementConfig elementConf = new ElementConfig();
    
    private static final double PROFILE_FIT_WIDTH = 204;
    private static final double PROFILE_FIT_HEIGHT = 168;
    private static final double IMAGE_SIZE = 168;
    private static final String TARGET_DIRECTORY = "pfp/";
    
    private File selectedFile;
    
    private int userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String role;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label usernamePlaceholder;
    @FXML
    private Button backButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private Button submitButton;
    @FXML
    private Label rolePlaceholder;
    @FXML
    private ImageView profilePhoto;
    @FXML
    private Button fileChooseButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            elementConf.loadProfilePicture(username, profilePhoto, IMAGE_SIZE);
        });
    }
    
    public static EditProfilePageController getInstance() {
        return instance;
    }
    
    public void loadUserContents(String userInput) {
        
        User user = dbConf.getUserByUsername(userInput);
        
        userId = user.getId();
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        lastName = user.getLastName();
        username = user.getUsername();
        role = user.getRole();
        
        firstNameField.setText(firstName);
        middleNameField.setText(middleName);
        lastNameField.setText(lastName);
        usernamePlaceholder.setText(username);
        rolePlaceholder.setText(role);
    }
    
    private boolean verifyInput(Stage currentStage, String fName, String lName) throws Exception {
        if(fName.isEmpty()) {
            alertConf.showEditProfileErrorAlert(currentStage, "First name field must not be empty.");
            return true;
        }
        
        if(lName.isEmpty()) {
            alertConf.showEditProfileErrorAlert(currentStage, "Last name field must not be empty.");
            return true;
        }
        return false;
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
        
        System.out.println(imagePath);
        
        if(dbConf.getImagePath(username) == null) {
            sql = "INSERT INTO user_image (user_id, image_path) VALUES (?, ?)";
            db.insertData(sql, userId, imagePath);
            System.out.println("User Image updated successfully!");
            return;
        }
        
        sql = "UPDATE user_image SET image_path = ? WHERE user_id = ?";
        db.updateData(sql, imagePath, userId);
        System.out.println("User Image updated successfully!");
    }
        
    @FXML
    private void backButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage stage = (Stage)rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String fName = firstNameField.getText();
        String mName = middleNameField.getText();
        String lName = lastNameField.getText();
        
        String sql = "UPDATE user SET first_name = ?, middle_name = ?, last_name = ? WHERE username = ?";
        
        if(verifyInput(currentStage, fName, lName)) 
            return;
        
        if(db.updateData(sql, fName, mName, lName, username)) {
                System.out.println("User updated successfully!");
                alertConf.showAlert(Alert.AlertType.INFORMATION, "User Update Successful", "User Updated Succesfully!", currentStage);
                
                moveAndSaveImageToDatabase();
                
                AdminPageController adminController = AdminPageController.getInstance();
                TeamMemberMainPageController teamMemberController = TeamMemberMainPageController.getInstance();
                ProfilePageController profileController = ProfilePageController.getInstance();
                
                double imageSize = 50;
                
                switch(role) {
                    case "admin":
                        elementConf.loadProfilePicture(username, adminController.getProfileButton(), imageSize);
                        break;
                    case "team member":
                        elementConf.loadProfilePicture(username, teamMemberController.getProfileButton(), imageSize);
                        break;
                }
                elementConf.loadProfilePicture(username, profileController.getProfilePhoto(), IMAGE_SIZE);
                profileController.loadUser(username);
                currentStage.close();
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
            return;
        }
        
        Image image = new Image(selectedFile.toURI().toString());
        
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        double viewportSize = Math.min(imageWidth, imageHeight);

        Rectangle2D viewPort = new Rectangle2D(
                (imageWidth - viewportSize) / 2,
                (imageHeight - viewportSize) / 2,
                viewportSize,
                viewportSize
       );
        
        profilePhoto.setImage(image);
        profilePhoto.setViewport(viewPort);
        profilePhoto.setFitHeight(PROFILE_FIT_HEIGHT);
        profilePhoto.setFitWidth(PROFILE_FIT_WIDTH);
        profilePhoto.setPreserveRatio(true);
        profilePhoto.setSmooth(true);
        profilePhoto.setCache(true);
        
        elementConf.setToCircle(IMAGE_SIZE, profilePhoto);
    }   
}
