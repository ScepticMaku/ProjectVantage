/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.superadmin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class SuperAdminPageController implements Initializable {

    @FXML
    private Rectangle rectangle;
    @FXML
    private Pane userManagementButton;
    @FXML
    private BorderPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void loadPage(String targetFXML) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(targetFXML));
        rootPane.setCenter(root);
    }

    @FXML
    private void userManagementButtonActionHandler(MouseEvent event) throws Exception {
        loadPage("/projectvantage/fxml/superadmin/UserManagementPage.fxml");
    }
    
}
