/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;
import projectvantage.utility.AlertConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class ForgotPasswordPageController implements Initializable {
    
    PageConfig pageConf = new PageConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    AlertConfig alertConf = new AlertConfig();

    @FXML
    private TextField emailField;
    @FXML
    private Button resetPasswordButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private void ResetPassword(Event event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String emailInput = emailField.getText();
        
         if(emailInput.isEmpty()) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Email Error", "Email must not be empty.", currentStage);
            return;
        }
        
        User user = dbConf.getUserByEmail(emailInput);
        
        String targetFXML = "/projectvantage/fxml/misc/ResetPasswordPage.fxml";
        
        if(user == null) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Email Error", "Email not found.", currentStage);
            return;
        }
        
        pageConf.switchToAuthenticator(event, getClass(), rootPane, targetFXML, "Reset Password", emailInput);
    }
    
    private boolean isEnterPressed(KeyEvent event) throws Exception {
        return event.getCode() == KeyCode.ENTER;
    }

    @FXML
    private void emailFieldKeyPressedHandler(KeyEvent event) throws Exception {
        if(isEnterPressed(event)) {
            ResetPassword(event);
        }
    }

    @FXML
    private void resetPasswordButtonMouseClickHandler(MouseEvent event) throws Exception {
        ResetPassword(event);
    }

    @FXML
    private void cancelButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage stage = (Stage)rootPane.getScene().getWindow();
        stage.close();
    }
    
}
