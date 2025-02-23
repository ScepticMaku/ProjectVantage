/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.superadmin;

import projectvantage.utility.Config;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class SuperAdminPageController implements Initializable {

    private BorderPane rootPane;
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

    private void userManagementButtonActionHandler(MouseEvent event) throws Exception {
        loadPage("/projectvantage/fxml/superadmin/UserManagementPage.fxml");
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
        Image newImage = new Image("/projectvantage/resources/icons/dashboard-icon-selected.png");
        dashboardButtonLabel.setTextFill(Color.web("#2f9efe"));
        dashboardButtonIndicator.setOpacity(1.0);
        dashboardButtonIcon.setImage(newImage);
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
        Image newImage = new Image("/projectvantage/resources/icons/project-icon-selected.png");
        projectButtonLabel.setTextFill(Color.web("#2f9efe"));
        projectButtonIndicator.setOpacity(1.0);
        projectButtonIcon.setImage(newImage);
    }

    @FXML
    private void projectButtonMousePressHandler(MouseEvent event) {
        projectButtonBG.setFill(Color.web("#eeeeee"));
    }
}
