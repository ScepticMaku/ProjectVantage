/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    
    private String username;
    private String role;
    
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            if(!role.equals("admin")) {
                adminSection.setOpacity(0.0);
            }
        });
    }    
    
    public static SettingsPageController getInstance() {
        return instance;
    }
    
    public void setUsername(String username) {
        this.username = username;
        
        User user = databaseConf.getUserByUsername(username);
        
        this.role = user.getRole();
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
