/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.superadmin;

import projectvantage.utility.Config;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class SuperAdminPageController implements Initializable {
    
    @FXML
    private Pane titleBar;
    @FXML
    private Rectangle closeButtonBG;
    private Rectangle minimizeButtonBG;
    @FXML
    private Group closeButton;
    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Group dashboardButton;
    @FXML
    private ImageView dashboardButtonIcon;
    @FXML
    private Label dashboardButtonLabel;
    @FXML
    private Circle dashboardButtonIndicator;
    @FXML
    private Rectangle dashboardButtonBG;
    @FXML
    private Group projectButton;
    @FXML
    private ImageView projectButtonIcon;
    @FXML
    private Label projectButtonLabel;
    @FXML
    private Circle projectButtonIndicator;
    @FXML
    private Rectangle projectButtonBG;
    @FXML
    private Rectangle logoutButtonBG;
    @FXML
    private ImageView logoutButtonIcon;
    @FXML
    private Label logoutButtonLabel;
    @FXML
    private Group logoutButton;
    @FXML
    private Rectangle teamButtonBG;
    @FXML
    private Circle logoutButtonIndicator;
    @FXML
    private Group teamButton;
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
    private ImageView profileButton;
    @FXML
    private ImageView notificationButton;
    @FXML
    private BorderPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    Config config = new Config();

    private double xOffset = 0;
    private double yOffset = 0;
    
    private void loadPage(String targetFXML) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(targetFXML));
        rootPane.setCenter(root);
    }
    
    public void hoverIcon(ImageView image) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), image);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.play();
    }
    
    public void unhoverIcon(ImageView image) {
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
        config.fadeOut(closeButtonBG);
    }

    @FXML
    private void closeButtonMouseEnterHandler(MouseEvent event) {
        config.fadeIn(closeButtonBG);
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
        config.fadeOut(dashboardButtonBG);
    }

    @FXML
    private void dashboardButtonMouseEnteredHandler(MouseEvent event) {
        config.fadeIn(dashboardButtonBG);
    }

    @FXML
    private void dashboardButtonMouseClickHandler(MouseEvent event) {
        config.setSelected("/projectvantage/resources/icons/dashboard-icon-selected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        
        config.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/team-icon-unselected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        config.setUnselected("projectvantage/resources/icons/user-icon-unselected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void dashboardMousePressHandler(MouseEvent event) {
        dashboardButtonBG.setFill(Color.web("#eeeeee"));
    }

    @FXML
    private void projectButtonMouseReleaseHandler(MouseEvent event) {
        projectButtonBG.setFill(Color.web("#f5f5f5"));
    }

    @FXML
    private void projectButtonMouseExitHandler(MouseEvent event) {
        config.fadeOut(projectButtonBG);
    }

    @FXML
    private void projectButtonMouseEnterHandler(MouseEvent event) {
        config.fadeIn(projectButtonBG);
    }

    @FXML
    private void projectButtonMouseClickHandler(MouseEvent event) {
        config.setSelected("/projectvantage/resources/icons/project-icon-selected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        
        config.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/team-icon-unselected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        config.setUnselected("projectvantage/resources/icons/user-icon-unselected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
    }

    @FXML
    private void projectButtonMousePressHandler(MouseEvent event) {
        projectButtonBG.setFill(Color.web("#eeeeee"));
    }

    @FXML
    private void logoutButtonMouseReleaseHandler(MouseEvent event) {
        logoutButtonBG.setFill(Color.web("#f5f5f5"));
        config.setUnselected("/projectvantage/resources/icons/signout-icon-unselected.png", logoutButtonLabel, logoutButtonIndicator, logoutButtonIcon);
    }

    @FXML
    private void logoutButtonMouseExitHandler(MouseEvent event) {
        config.fadeOut(logoutButtonBG);
    }

    @FXML
    private void logoutButtonMouseEnterHandler(MouseEvent event) {
        config.fadeIn(logoutButtonBG);
    }

    @FXML
    private void logoutButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Log out Confirmation.");
        alert.setContentText("are you sure you want to log out?");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(currentStage);
        
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK)
                try {
                    config.switchScene(getClass(), event, "/projectvantage/fxml/authentication/Authentication.fxml");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private void logoutButtonMousePressHandler(MouseEvent event) {
        logoutButtonBG.setFill(Color.web("#eeeeee"));
        config.setSelected("/projectvantage/resources/icons/signout-icon-selected.png", logoutButtonLabel, logoutButtonIndicator, logoutButtonIcon);
    }

    @FXML
    private void exitButtonMouseReleaseHandler(MouseEvent event) {
        teamButtonBG.setFill(Color.web("#f5f5f5"));
    }

    @FXML
    private void teamButtonMouseExitHandler(MouseEvent event) {
        config.fadeOut(teamButtonBG);
    }

    @FXML
    private void teamButtonMouseEnterHandler(MouseEvent event) {
        config.fadeIn(teamButtonBG);
    }

    @FXML
    private void teamButtonMouseClickHandler(MouseEvent event) {
        config.setSelected("/projectvantage/resources/icons/team-icon-selected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        
        config.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        config.setUnselected("projectvantage/resources/icons/user-icon-unselected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
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
        config.fadeOut(taskButtonBG);
    }

    @FXML
    private void taskButtonMouseEnterHandler(MouseEvent event) {
        config.fadeIn(taskButtonBG);
    }

    @FXML
    private void taskButtonMouseClickHandler(MouseEvent event) {
        config.setSelected("/projectvantage/resources/icons/task-icon-selected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        
        config.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/team-icon-unselected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        config.setUnselected("projectvantage/resources/icons/user-icon-unselected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
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
        config.fadeOut(userButtonBG);
    }

    @FXML
    private void userButtonMouseEnterHandler(MouseEvent event) {
        config.fadeIn(userButtonBG);
    }

    @FXML
    private void userButtonMouseClickHandler(MouseEvent event) throws Exception {
        config.setSelected("projectvantage/resources/icons/user-icon-selected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
        loadPage("/projectvantage/fxml/superadmin/UserManagementPage.fxml");
        
        config.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        config.setUnselected("/projectvantage/resources/icons/team-icon-unselected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/settings-icon-unselected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
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
        config.fadeOut(settingsButtonBG);
    }

    @FXML
    private void settingsButtonMouseEnterHandler(MouseEvent event) {
        config.fadeIn(settingsButtonBG);
    }

    @FXML
    private void settingsButtonMouseClickHandler(MouseEvent event) {
        config.setSelected("/projectvantage/resources/icons/settings-icon-selected.png", settingsButtonLabel, settingsButtonIndicator, settingsButtonIcon);
        
        config.setUnselected("/projectvantage/resources/icons/project-icon-unselected.png", projectButtonLabel, projectButtonIndicator, projectButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/dashboard-icon-unselected.png", dashboardButtonLabel, dashboardButtonIndicator, dashboardButtonIcon);
        config.setUnselected("/projectvantage/resources/icons/task-icon-unselected.png", taskButtonLabel, taskButtonIndicator, taskButtoIcon);
        config.setUnselected("/projectvantage/resources/icons/team-icon-unselected.png", teamButtonLabel, teamButtonIndicator, teamButtonIcon);
        config.setUnselected("projectvantage/resources/icons/user-icon-unselected.png", userButtonLabel, userButtonIndicator, userButtonIcon);
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
        config.releaseIcon(profileButton);
    }

    @FXML
    private void profileButtonMouseClickHandler(MouseEvent event) {
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
        config.releaseIcon(notificationButton);
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
