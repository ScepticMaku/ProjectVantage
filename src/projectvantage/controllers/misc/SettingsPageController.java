/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.SessionConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class SettingsPageController implements Initializable {
    
    private static SettingsPageController instance;

    PageConfig pageConf = new PageConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    
    private String emailAddress;
    private String phoneNumber;
    private String role;
    private String secretKey;
    
    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private Button logsButton;
    @FXML
    private Pane adminSection;
    @FXML
    private Label authenticationLabel;
    @FXML
    private Button enableAuthenticationButton;
    @FXML
    private Button disableAuthenticationButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNumberTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            
            SessionConfig sessionConf = SessionConfig.getInstance();
            
            role = sessionConf.getRole();
            emailAddress = sessionConf.getEmail();
            phoneNumber = sessionConf.getPhoneNumber();
            secretKey = sessionConf.getSecretKey();
            
            if(!role.equals("admin")) {
                adminSection.setOpacity(0.0);
            }
            loadContent();
        });
    }    
    
    public static SettingsPageController getInstance() {
        return instance;
    }
    
    private void loadContent() {
        emailTextField.setText(emailAddress);
        phoneNumberTextField.setText(phoneNumber);
        
        if(secretKey != null) {
            authenticationLabel.setText("Enabled");
            disableAuthenticationButton.setVisible(true);
            enableAuthenticationButton.setVisible(false);
        }
    }
    
    @FXML
    private void logsButtonMouseClicklHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/admin/EventLogPage.fxml", "Event Logs",backgroundPane);
    }

    @FXML
    private void enableAuthenticationButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void disableAuthenticationButtonMouseClickHandler(MouseEvent event) {
    }
    
}
