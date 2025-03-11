/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.authentication;

import projectvantage.utility.Config;
import projectvantage.utility.PageConfig;
import projectvantage.utility.ElementConfig;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class AuthenticationController implements Initializable {
    
    Config config = new Config();
    PageConfig pageConf = new PageConfig();
    ElementConfig elementConf = new ElementConfig();
    
    private LoginController loginControl;
    private RegisterController registerControl;  
    
    @FXML
    private StackPane rootPane;
    @FXML
    private Rectangle rectangle;;
    @FXML
    private Pane loginPane;
    @FXML
    private Pane otherPane;
    @FXML
    private Pane titlePane;
    @FXML
    private Pane backgroundPane;
    @FXML
    private ImageView closeButton;
    
    private static AuthenticationController instance;
    @FXML
    private Rectangle exitButtonBG;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }
    
    public static AuthenticationController getInstance() {
        return instance;
    }
    
    public Pane getOtherPane() {
        return otherPane;
    }
    
    public Pane getLoginPane() {
        return loginPane;
    }
    
    public Pane getTitlePane() {
        return titlePane;
    }

    @FXML
    private void closeButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        config.showAlert(AlertType.CONFIRMATION, "Exit Confirmtaion.", "Do you want to exit?", currentStage);
    }

    @FXML
    private void closeButtonMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(exitButtonBG);
    }

    @FXML
    private void closeButtonMouseEnterHandler(MouseEvent event) {
        elementConf.fadeIn(exitButtonBG);
    }

    @FXML
    private void closeButtonMousePressHandler(MouseEvent event) {
        exitButtonBG.setFill(Color.web("#971111"));
    }
    
    @FXML
    private void closeButtonMouseReleaseHandler(MouseEvent event) {
        exitButtonBG.setFill(Color.web("#d71515"));
    }   
}