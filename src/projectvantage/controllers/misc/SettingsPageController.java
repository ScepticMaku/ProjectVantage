/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.PageConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class SettingsPageController implements Initializable {

    PageConfig pageConf = new PageConfig();
    
    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private Button logsButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logsButtonMouseClicklHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/admin/EventLogPage.fxml", "Event Logs",backgroundPane);
    }
    
}
