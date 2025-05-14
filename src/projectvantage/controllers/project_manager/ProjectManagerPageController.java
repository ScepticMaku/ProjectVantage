/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.project_manager;

import projectvantage.utility.SessionConfig;
import projectvantage.controllers.misc.SettingsPageController;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.PageConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import projectvantage.models.User;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class ProjectManagerPageController implements Initializable {
    
    private static ProjectManagerPageController instance;
    
    DatabaseConfig databaseConf = new DatabaseConfig();
    ElementConfig elementConf = new ElementConfig();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    
    private static final double IMAGE_SIZE = 50;
    
    private String username;
    private int userId;

    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Group dashboardButton;
    @FXML
    private Rectangle dashboardButtonBG;
    @FXML
    private ImageView dashboardButtonIcon;
    @FXML
    private Label dashboardButtonLabel;
    @FXML
    private Circle dashboardButtonIndicator;
    @FXML
    private Group settingsButton;
    @FXML
    private Rectangle settingsButtonBG;
    @FXML
    private ImageView settingsButtonIcon;
    @FXML
    private Label settingsButtonLabel;
    @FXML
    private Circle settingsButtonIndicator;
    @FXML
    private Group logoutButton;
    @FXML
    private Rectangle logoutButtonBG;
    @FXML
    private ImageView logoutButtonIcon;
    @FXML
    private Label logoutButtonLabel;
    @FXML
    private Circle logoutButtonIndicator;
    @FXML
    private BorderPane rootPane;
    @FXML
    private ImageView profileButton;
    @FXML
    private ImageView notificationButton;
    @FXML
    private Group projectButton;
    @FXML
    private Rectangle projectButtonBG;
    @FXML
    private ImageView projectButtonIcon;
    @FXML
    private Label projectButtonLabel;
    @FXML
    private Circle projectButtonIndicator;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            SessionConfig sessionConf = SessionConfig.getInstance();
            
            username = sessionConf.getUsername();
            userId = sessionConf.getId();
            
            elementConf.loadProfilePicture(username, profileButton, IMAGE_SIZE);
            
            loadDashboardPage();
        });
    }    
    
    public static ProjectManagerPageController getInstance() {
        return instance;
    }
    
    public BorderPane getBackgroundPane() {
        return rootPane;
    }
    
    public void setUsername(String username) {
        this.username = username;
        
        User user = databaseConf.getUserByUsername(username);
        
        this.userId = user.getId();
    }
    
   private void loadDashboardPage() {
        try{
            FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/projectvantage/fxml/project_manager/ProjectManagerDashboardPage.fxml"));
            rootPane.setCenter(dashboardLoader.load());

            ProjectManagerDashboardPageController dashboardController = dashboardLoader.getController();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
   
   public void loadPage(String targetFXML, String title) {
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource(targetFXML));
            rootPane.setCenter(root);
            currentStage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
            alertConf.showDatabaseErrorAlert(currentStage, "Failed to load page: " + e.getMessage());
        }
    }

    @FXML
    private void dashboardMouseReleaseHandler(MouseEvent event) {
        dashboardButtonBG.setFill(Color.web("#f5f5f5"));
    }

    @FXML
    private void dashboardMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(dashboardButtonBG);
    }

    @FXML
    private void dashboardButtonMouseEnteredHandler(MouseEvent event) {
        elementConf.fadeIn(dashboardButtonBG);
    }

    @FXML
    private void dashboardButtonMouseClickHandler(MouseEvent event) {
        loadDashboardPage();
        
        elementConf.setSelected("/projectvantage/resources/icons/dashboard-icon-selected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        
        elementConf.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
        
    }   

    @FXML
    private void dashboardMousePressHandler(MouseEvent event) {
        dashboardButtonBG.setFill(Color.web("#eeeeee"));
    }


    @FXML
    private void settingsButtonMouseReleaseHandler(MouseEvent event) {
    settingsButtonBG.setFill(Color.web("#f5f5f5"));
    }

    @FXML
    private void settingsButtonMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(settingsButtonBG);
    }

    @FXML
    private void settingsButtonMouseEnterHandler(MouseEvent event) {
        elementConf.fadeIn(settingsButtonBG);
    }

    @FXML
    private void settingsButtonMouseClickHandler(MouseEvent event) {
        String settingsPageFXML = "/projectvantage/fxml/misc/SettingsPage.fxml";
        loadPage(settingsPageFXML, "Settings");
        
        elementConf.setSelected("/projectvantage/resources/icons/settings-icon-selected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
        
        elementConf.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
    }

    @FXML
    private void settingsButtonMousePressHandler(MouseEvent event) {
        settingsButtonBG.setFill(Color.web("#eeeeee")); 
    }

    @FXML
    private void logoutButtonMouseReleaseHandler(MouseEvent event) {
        logoutButtonBG.setFill(Color.web("#f5f5f5"));
        elementConf.setUnselected("/projectvantage/resources/icons/signout-icon-unselected.png", logoutButtonLabel, logoutButtonIndicator, logoutButtonIcon);
    }

    @FXML
    private void logoutButtonMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(logoutButtonBG);
    }

    @FXML
    private void logoutButtonMouseEnterHandler(MouseEvent event) {
        elementConf.fadeIn(logoutButtonBG);
    }

    @FXML
    private void logoutButtonMouseClickHandler(MouseEvent event) {
        alertConf.showLogoutConfirmationAlert(rootPane, event, userId);
    }

    @FXML
    private void logoutButtonMousePressHandler(MouseEvent event) {
        logoutButtonBG.setFill(Color.web("#eeeeee"));
        elementConf.setSelected("/projectvantage/resources/icons/signout-icon-selected.png", logoutButtonLabel, logoutButtonIndicator, logoutButtonIcon);
    }

    @FXML
    private void profileButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(profileButton);
    }

    @FXML
    private void profileButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(profileButton);
    }

    @FXML
    private void profileButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(profileButton);
    }

    @FXML
    private void profileButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        String fxmlLocation = "/projectvantage/fxml/misc/ProfilePage.fxml";
        pageConf.loadProfilePage(fxmlLocation, username, backgroundPane, rootPane);
        currentStage.setTitle("Profile");
    }

    @FXML
    private void profileButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(profileButton);
    }

    @FXML
    private void notificationButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(notificationButton);
    }

    @FXML
    private void notificationButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(notificationButton);
    }

    @FXML
    private void notificationButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(notificationButton);
    }

    @FXML
    private void notificationButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void notificationButtonMousePressHandler(MouseEvent event) {
        elementConf.pressIcon(notificationButton);
        
    }

    @FXML
    private void projectButtonMouseReleaseHandler(MouseEvent event) {
        projectButtonBG.setFill(Color.web("#f5f5f5"));
    }

    @FXML
    private void projectButtonMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(projectButtonBG);
    }

    @FXML
    private void projectButtonMouseEnterHandler(MouseEvent event) {
        elementConf.fadeIn(projectButtonBG);
    }

    @FXML
    private void projectButtonMouseClickHandler(MouseEvent event) {
        String projectFXML = "/projectvantage/fxml/project_manager/ProjectPage.fxml";
        loadPage(projectFXML, "Projects");
        
        elementConf.setSelected("/projectvantage/resources/icons/project-icon-selected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        
        elementConf.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void projectButtonMousePressHandler(MouseEvent event) {
        projectButtonBG.setFill(Color.web("#eeeeee"));
    }
    
}
