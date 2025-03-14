/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_member;

import projectvantage.utility.Config;
import projectvantage.utility.dbConnect;
import projectvantage.utility.PageConfig;
import projectvantage.utility.ElementConfig;
import projectvantage.controllers.misc.ProfilePageController;

import java.net.URL;
import java.sql.ResultSet;
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
 * @author PC15
 */
public class TeamMemberMainPageController implements Initializable {
    
    Config config = new Config();
    PageConfig pageConf = new PageConfig();
    ElementConfig elementConf = new ElementConfig();
    ProfilePageController profileController = ProfilePageController.getInstance();
    
    private static TeamMemberMainPageController instance;
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    private String username;

    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private Pane titleBar;
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
    @FXML
    private Label titlebarLabel;
    @FXML
    private AnchorPane rootPane1;
    @FXML
    private Group welcomeMessageLabel;
    @FXML
    private Label usernameLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
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
            System.out.println(e);
            config.showErrorMessage("There was a problem with the database", "Database Error:", currentStage);
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
    
    public Label getTitlebarLabel() {
        return titlebarLabel;
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
    private void closeButtonMouseReleaseHandler(MouseEvent event) {
        closeButtonBG.setFill(Color.web("#d71515"));
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
    private void titleBarOnMouseDraggedHandler(MouseEvent event) {
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    private void titleBarOnMousePressedHandler(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void dashboardMouseReleaseHandler(MouseEvent event) {
        closeButtonBG.setFill(Color.web("#d71515"));
        
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
        String fxmlLocation = "/projectvantage/fxml/team_member/TeamMemberDashboardPage.fxml";
        String user = getInstance().getUsername();
        
        elementConf.setSelected("/projectvantage/resources/icons/dashboard-icon-selected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        pageConf.loadDashboardPage(fxmlLocation, user, backgroundPane, rootPane);
        titlebarLabel.setText("Dashboard");
        
        elementConf.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        elementConf.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void dashboardMousePressHandler(MouseEvent event) {
        closeButtonBG.setFill(Color.web("#971111"));
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
    private void profileButtonMouseClickHandler(MouseEvent event) {
//        String fxmlLocation = "/projectvantage/fxml/misc/ProfilePage.fxml";
//        String user = getInstance().getUsername();
//        pageConf.loadProfilePage(fxmlLocation, user, backgroundPane, rootPane);
        String fxmlLocation = "/projectvantage/fxml/misc/ProfilePage.fxml";
        String user = getInstance().getUsername();
        pageConf.loadProfilePage(fxmlLocation, user, backgroundPane, rootPane);
        titlebarLabel.setText("Profile");
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
