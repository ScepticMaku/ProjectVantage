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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
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
 * @author Mark Work Account
 */
public class AdminPageController implements Initializable {
    
    PageConfig pageConf = new PageConfig();
    ElementConfig elementConf = new ElementConfig();
    Config config = new Config();
    ProfilePageController profileController = ProfilePageController.getInstance();
    AdminDashboardPageController dashboardController = AdminDashboardPageController.getInstance();
    
    private static AdminPageController instance;
    private String username;
    
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private Pane titleBar;
    @FXML
    private Label titlebarLabel;
    @FXML
    private Group closeButton;
    @FXML
    private Rectangle closeButtonBG;
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
    public static AdminPageController getInstance() {
        return instance;
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
    
    public Label getTitlebarLabel() {
        return titlebarLabel;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }
    
    public void loadPage(String targetFXML) {
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource(targetFXML));
            rootPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
            config.showErrorMessage("There was a problem with the database", "Database Error:", currentStage);
        }
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
    private void titleBarOnMousePressedHandler(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void titleBarOnMouseDraggedHandler(MouseEvent event) {
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    private void closeButtonMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(closeButtonBG);
    }

    @FXML
    private void closeButtonMouseEnterHandler(MouseEvent event) {
        elementConf.fadeIn(closeButtonBG);
    }

    @FXML
    private void closeButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        config.showAlert(Alert.AlertType.CONFIRMATION, "Exit Confirmtaion.", "Do you want to exit?", currentStage);
    }
    
    @FXML
    private void closeButtonMousePressHandler(MouseEvent event) {
        closeButtonBG.setFill(Color.web("#971111"));
    }
    
    @FXML
    private void closeButtonMouseReleaseHandler(MouseEvent event) {
        closeButtonBG.setFill(Color.web("#d71515"));
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
        String user = getInstance().getUsername();
        String fxmlLocation = "/projectvantage/fxml/admin/AdminDashboardPage.fxml";
        
        elementConf.setSelected("/projectvantage/resources/icons/dashboard-icon-selected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        pageConf.loadDashboardPage(fxmlLocation, user, backgroundPane, rootPane);
        titlebarLabel.setText("Dashboard");
        
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
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Log out Confirmation.");
        alert.setContentText("are you sure you want to log out?");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(currentStage);
        
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK)
                try {
                    pageConf.switchScene(getClass(), event, "/projectvantage/fxml/authentication/Login.fxml");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
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
        
        elementConf.setSelected("projectvantage/resources/icons/user-icon-selected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        loadPage("/projectvantage/fxml/admin/UserManagementPage.fxml");
        titlebarLabel.setText("Users");
        
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
    private void settingsButtonMouseClickHandler(MouseEvent event) {
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
        hoverIcon(profileButton);
    }
    
    @FXML
    private void profileButtonMouseExitHandler(MouseEvent event) {
        unhoverIcon(profileButton);
    }

    @FXML
    private void profileButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(profileButton);
    }

    @FXML
    private void profileButtonMouseClickHandler(MouseEvent event) {
        String fxmlLocation = "/projectvantage/fxml/misc/ProfilePage.fxml";
        String user = getInstance().username;
        pageConf.loadProfilePage(fxmlLocation, user, backgroundPane, rootPane);
        titlebarLabel.setText("Profile");
    }

    @FXML
    private void profileButtonMousePressHandler(MouseEvent event) {
//        config.pressIcon(profileButton);
        profileButton.setScaleX(0.9);
        profileButton.setScaleY(0.9);
    }
    
    @FXML
    private void notificationButtonMouseEnterHandler(MouseEvent event) {
        hoverIcon(notificationButton);
    }
    
    @FXML
    private void notificationButtonMouseExitHandler(MouseEvent event) {
        unhoverIcon(notificationButton);
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
