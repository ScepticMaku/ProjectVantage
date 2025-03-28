/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.controllers.misc.ProfilePageController;
import projectvantage.utility.Config;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;

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

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class AdminPageController implements Initializable {
    
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    ElementConfig elementConf = new ElementConfig();
    Config config = new Config();
    DatabaseConfig dbConf = new DatabaseConfig();
    ProfilePageController profileController = ProfilePageController.getInstance();
    
    private static final double IMAGE_SIZE = 50;
    
    private static AdminPageController instance;
    private String username;

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
    private Group projectButton;
    @FXML
    private Rectangle projectButtonBG;
    @FXML
    private ImageView projectButtonIcon;
    @FXML
    private Label projectButtonLabel;
    @FXML
    private Circle projectButtonIndicator;
    @FXML
    private Group teamButton;
    @FXML
    private Rectangle teamButtonBG;
    @FXML
    private ImageView teamButtonIcon;
    @FXML
    private Label teamButtonLabel;
    @FXML
    private Circle teamButtonIndicator;
    @FXML
    private Group taskButton;
    @FXML
    private Rectangle taskButtonBG;
    @FXML
    private ImageView taskButtoIcon;
    @FXML
    private Label taskButtonLabel;
    @FXML
    private Circle taskButtonIndicator;
    @FXML
    private Group userButton;
    @FXML
    private Rectangle userButtonBG;
    @FXML
    private ImageView userButtonIcon;
    @FXML
    private Label userButtonLabel;
    @FXML
    private Circle userButtonIndicator;
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
    private BorderPane rootPane;
    @FXML
    private ImageView profileButton;
    @FXML
    private ImageView notificationButton;
    
     /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            elementConf.loadProfilePicture(username, profileButton, IMAGE_SIZE);
            
            loadDashboardPage();
        });
    }
    
     public static AdminPageController getInstance() {
        return instance;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String userInput) {
        User user = dbConf.getUserByUsername(userInput);
        
        username = user.getUsername();
    }
    
    public AnchorPane getBackgroundPane() {
        return backgroundPane;
    }
    
    public BorderPane getRootPane() {
        return rootPane;
    }
    
    public ImageView getProfileButton() {
        return profileButton;
    }
    
    public void loadPage(String targetFXML, String title) {
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource(targetFXML));
            rootPane.setCenter(root);
            currentStage.setTitle(title);
        } catch (Exception e) {
            alertConf.showDatabaseErrorAlert(currentStage, e.getMessage());
        }
    }
    
    public void loadDashboardPage() {
        try{
            FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/projectvantage/fxml/admin/AdminDashboardPage.fxml"));
            rootPane.setCenter(dashboardLoader.load());

            AdminDashboardPageController dashboardController = dashboardLoader.getController();
            dashboardController.loadContent(username);
        } catch(Exception e) {
            e.printStackTrace();
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
    private void dashboardButtonMouseClickHandler(MouseEvent event) throws Exception {
        loadDashboardPage();
        
        
        elementConf.setSelected("/projectvantage/resources/icons/dashboard-icon-selected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        
        
        elementConf.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/team-icon-unselected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        elementConf.setUnselected("projectvantage/resources/icons/user-icon-unselected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void dashboardMousePressHandler(MouseEvent event) {
        taskButtonBG.setFill(Color.web("#eeeeee"));
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
        elementConf.setSelected("/projectvantage/resources/icons/project-icon-selected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        
        elementConf.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/team-icon-unselected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        elementConf.setUnselected("projectvantage/resources/icons/user-icon-unselected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void projectButtonMousePressHandler(MouseEvent event) {
        projectButtonBG.setFill(Color.web("#eeeeee"));
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
    private void logoutButtonMouseClickHandler(MouseEvent event) throws Exception {
        alertConf.showLogoutConfirmationAlert(rootPane, event);
    }

    @FXML
    private void logoutButtonMousePressHandler(MouseEvent event) {
        logoutButtonBG.setFill(Color.web("#eeeeee"));
        elementConf.setSelected("/projectvantage/resources/icons/signout-icon-selected.png", logoutButtonLabel, logoutButtonIndicator, logoutButtonIcon);
    }

    @FXML
    private void exitButtonMouseReleaseHandler(MouseEvent event) {
        logoutButtonBG.setFill(Color.web("#f5f5f5"));
    }

    @FXML
    private void teamButtonMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(teamButtonBG);
    }

    @FXML
    private void teamButtonMouseEnterHandler(MouseEvent event) {
        elementConf.fadeIn(teamButtonBG);
    }

    @FXML
    private void teamButtonMouseClickHandler(MouseEvent event) {
        elementConf.setSelected("/projectvantage/resources/icons/team-icon-selected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
      
        elementConf.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        elementConf.setUnselected("projectvantage/resources/icons/user-icon-unselected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void exitButtonMousePressHandler(MouseEvent event) {
        teamButtonBG.setFill(Color.web("#eeeeee"));
    }

    @FXML
    private void taskButtonMouseReleaseHandler(MouseEvent event) {
        taskButtonBG.setFill(Color.web("#f5f5f5"));
    }

    @FXML
    private void taskButtonMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(taskButtonBG);
    }

    @FXML
    private void taskButtonMouseEnterHandler(MouseEvent event) {
        elementConf.fadeIn(taskButtonBG);
    }

    @FXML
    private void taskButtonMouseClickHandler(MouseEvent event) {
        elementConf.setSelected("/projectvantage/resources/icons/task-icon-selected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        
        elementConf.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/team-icon-unselected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        elementConf.setUnselected("projectvantage/resources/icons/user-icon-unselected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void taskButtonMousePressHandler(MouseEvent event) {
        teamButtonBG.setFill(Color.web("#eeeeee"));
    }

    @FXML
    private void userButtonMouseReleaseHandler(MouseEvent event) {
        userButtonBG.setFill(Color.web("#f5f5f5"));
    }

    @FXML
    private void userButtonMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(userButtonBG);
    }

    @FXML
    private void userButtonMouseEnterHandler(MouseEvent event) {
        elementConf.fadeIn(userButtonBG);
    }

    @FXML
    private void userButtonMouseClickHandler(MouseEvent event) throws Exception {
        String userManagementPageFXML = "/projectvantage/fxml/admin/UserManagementPage.fxml";
        
        elementConf.setSelected("projectvantage/resources/icons/user-icon-selected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        loadPage(userManagementPageFXML, "Users");
        
        elementConf.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/team-icon-unselected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void userButtonMousePressHandler(MouseEvent event) {
        userButtonBG.setFill(Color.web("#eeeeee"));
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
    private void settingsButtonMouseClickHandler(MouseEvent event) throws Exception {
        String settingsPageFXML = "/projectvantage/fxml/misc/SettingsPage.fxml";
        loadPage(settingsPageFXML, "Settings");
        elementConf.setSelected("/projectvantage/resources/icons/settings-icon-selected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
        
        elementConf.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/team-icon-unselected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        elementConf.setUnselected("projectvantage/resources/icons/user-icon-unselected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
    }

    @FXML
    private void settingsButtonMousePressHandler(MouseEvent event) {
        settingsButtonBG.setFill(Color.web("#eeeeee")); 
    }
    
    @FXML
    private void profileButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(profileButton);
    }
    
    @FXML
    private void profileButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(profileButton);
    }

    @FXML
    private void profileButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(profileButton);
    }

    @FXML
    private void profileButtonMouseClickHandler(MouseEvent event) throws Exception {
        String fxmlLocation = "/projectvantage/fxml/misc/ProfilePage.fxml";
        String user = getInstance().username;
        pageConf.loadProfilePage(fxmlLocation, user, backgroundPane, rootPane);
    }

    @FXML
    private void profileButtonMousePressHandler(MouseEvent event) {
//        config.pressIcon(profileButton);
        profileButton.setScaleX(0.9);
        profileButton.setScaleY(0.9);
    }
    
    @FXML
    private void notificationButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(notificationButton);
    }
    
    @FXML
    private void notificationButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(notificationButton);
    }

    @FXML
    private void notificationButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(notificationButton);
    }

    @FXML
    private void notificationButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void notificationButtonMousePressHandler(MouseEvent event) {
        notificationButton.setScaleX(0.9);
        notificationButton.setScaleY(0.9);
    }
}