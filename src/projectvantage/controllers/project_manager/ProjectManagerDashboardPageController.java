/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.project_manager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class ProjectManagerDashboardPageController implements Initializable {
    
    private static ProjectManagerDashboardPageController instance;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label usernameLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance =  this;
    }    
    
    public static ProjectManagerDashboardPageController getInstance() {
        return instance;
    }
    
    public void loadContent(String user) {
        usernameLabel.setText(user);
    }
    
}
