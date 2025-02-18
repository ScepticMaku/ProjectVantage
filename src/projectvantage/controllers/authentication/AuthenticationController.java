/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.authentication;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class AuthenticationController implements Initializable {
    
    private Stage primaryStage;
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
        int result = JOptionPane.showConfirmDialog(null,"Do you want to exit?", "Exit confirmation.", JOptionPane.YES_NO_OPTION);
        
        if(result == JOptionPane.YES_OPTION){
            System.exit(0);
        }
            
    }

    @FXML
    private void closeButtonMouseExitHandler(MouseEvent event) {
        exitButtonBG.setVisible(false);
    }

    @FXML
    private void closeButtonMouseEnterHandler(MouseEvent event) {
        exitButtonBG.setVisible(true);
        exitButtonBG.setFill(Color.web("#d71515"));
    }

    @FXML
    private void closeButtonMousePressHandler(MouseEvent event) {
        exitButtonBG.setFill(Color.web("#971111"));
    }

        
}