/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.PageConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class ResetPasswordPageController implements Initializable {
    
    private static ResetPasswordPageController instance;
    
    DatabaseConfig dbConf = new DatabaseConfig();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    
    String username;

    @FXML
    private Label usernamePlaceholder;
    @FXML
    private Button submitButton;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }    
    
    public static ResetPasswordPageController getInstance() {
        return instance;
    }
    
    public void setUsername(String userInput) {
        
        User user = dbConf.getUserByUsername(userInput);
        
        username = user.getUsername();
        
        usernamePlaceholder.setText(username);
    }


    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void cancelButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.switchScene(getClass(), event, "/projectvantage/fxml/authentication/Login.fxml");
    }
    
}
