/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_member;

import projectvantage.utility.Config;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.ElementConfig;
import projectvantage.controllers.misc.ProfilePageController;


import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author PC15
 */
public class TeamMemberMainPageController implements Initializable {
    
    AlertConfig alertConf = new AlertConfig();
    Config config = new Config();
    PageConfig pageConf = new PageConfig();
    ElementConfig elementConf = new ElementConfig();
    ProfilePageController profileController = ProfilePageController.getInstance();
    
    private static TeamMemberMainPageController instance;
    
    private static final double IMAGE_SIZE = 50;
    
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
    
    public static TeamMemberMainPageController getInstance() {
        return instance;
    }
    
    public void loadPage(String targetFXML) {
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource(targetFXML));
            rootPane.setCenter(root);
        } catch (Exception e) {
            alertConf.showDatabaseErrorAlert(currentStage, e.getMessage());
        }
    }
    
    public void loadDashboardPage() {
        try{
            FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/projectvantage/fxml/team_member/TeamMemberDashboardPage.fxml"));
            rootPane.setCenter(dashboardLoader.load());

            TeamMemberDashboardPageController dashboardController = dashboardLoader.getController();
            dashboardController.loadContent(username);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
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
    
    private void hoverIcon(ImageView image) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), image);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.play();
    }
    
    private void unhoverIcon(ImageView image) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), image);
        scaleTransition.setFromX(1.1);
        scaleTransition.setFromY(1.1);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }

    @FXML
    private void dashboardMouseReleaseHandler(MouseEvent event) {
        
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
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        loadDashboardPage();
        currentStage.setTitle("Dashboard");
        
        elementConf.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void dashboardMousePressHandler(MouseEvent event) {
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
        
        elementConf.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void taskButtonMousePressHandler(MouseEvent event) {
        taskButtonBG.setFill(Color.web("#eeeeee"));
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
         elementConf.setSelected("/projectvantage/resources/icons/settings-icon-selected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
         
         elementConf.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
         elementConf.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
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
        alertConf.showLogoutConfirmationAlert(rootPane, event);
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
        unhoverIcon(profileButton);
    }

    @FXML
    private void profileButtonMouseEnterHandler(MouseEvent event) {
        hoverIcon(profileButton);
    }

    @FXML
    private void profileButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        String fxmlLocation = "/projectvantage/fxml/misc/ProfilePage.fxml";
        String user = getInstance().getUsername();
        pageConf.loadProfilePage(fxmlLocation, user, backgroundPane, rootPane);
        currentStage.setTitle("Profile");
    }

    @FXML
    private void profileButtonMousePressHandler(MouseEvent event) {
        profileButton.setScaleX(0.9);
        profileButton.setScaleY(0.9);
    }

    @FXML
    private void notificationButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(notificationButton);
        
    }

    @FXML
    private void notificationButtonMouseExitHandler(MouseEvent event) {
        unhoverIcon(notificationButton);
    }

    @FXML
    private void notificationButtonMouseEnterHandler(MouseEvent event) {
        hoverIcon(notificationButton);
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
