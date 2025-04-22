/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_manager;

import projectvantage.utility.AlertConfig;

import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class TeamManagerPageController implements Initializable {
    
    AlertConfig alertConf = new AlertConfig();
    
    private static TeamManagerPageController instance;
    
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
    }    
    
    public static TeamManagerPageController getInstance() {
        return instance;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void loadPage(String targetFXML, String title) {
        Stage currentStage = (Stage)backgroundPane.getScene().getWindow();
        
        
        try {
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
    }

    @FXML
    private void dashboardMouseExitHandler(MouseEvent event) {
    }

    @FXML
    private void dashboardButtonMouseEnteredHandler(MouseEvent event) {
    }

    @FXML
    private void dashboardButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void dashboardMousePressHandler(MouseEvent event) {
    }

    @FXML
    private void exitButtonMouseReleaseHandler(MouseEvent event) {
    }

    @FXML
    private void teamButtonMouseExitHandler(MouseEvent event) {
    }

    @FXML
    private void teamButtonMouseEnterHandler(MouseEvent event) {
    }

    @FXML
    private void teamButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void exitButtonMousePressHandler(MouseEvent event) {
    }

    @FXML
    private void settingsButtonMouseReleaseHandler(MouseEvent event) {
    }

    @FXML
    private void settingsButtonMouseExitHandler(MouseEvent event) {
    }

    @FXML
    private void settingsButtonMouseEnterHandler(MouseEvent event) {
    }

    @FXML
    private void settingsButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void settingsButtonMousePressHandler(MouseEvent event) {
    }

    @FXML
    private void logoutButtonMouseReleaseHandler(MouseEvent event) {
    }

    @FXML
    private void logoutButtonMouseExitHandler(MouseEvent event) {
    }

    @FXML
    private void logoutButtonMouseEnterHandler(MouseEvent event) {
    }

    @FXML
    private void logoutButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void logoutButtonMousePressHandler(MouseEvent event) {
    }

    @FXML
    private void profileButtonMouseReleaseHandler(MouseEvent event) {
    }

    @FXML
    private void profileButtonMouseExitHandler(MouseEvent event) {
    }

    @FXML
    private void profileButtonMouseEnterHandler(MouseEvent event) {
    }

    @FXML
    private void profileButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void profileButtonMousePressHandler(MouseEvent event) {
    }

    @FXML
    private void notificationButtonMouseReleaseHandler(MouseEvent event) {
    }

    @FXML
    private void notificationButtonMouseExitHandler(MouseEvent event) {
    }

    @FXML
    private void notificationButtonMouseEnterHandler(MouseEvent event) {
    }

    @FXML
    private void notificationButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void notificationButtonMousePressHandler(MouseEvent event) {
    }
    
}
