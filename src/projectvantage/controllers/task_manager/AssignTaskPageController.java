/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.task_manager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author markj
 */
public class AssignTaskPageController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button assignButton;
    @FXML
    private TableView<?> teamTable;
    @FXML
    private TableColumn<?, ?> teamColumn;
    @FXML
    private TableView<?> memberTable;
    @FXML
    private TableColumn<?, ?> memberColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void assignButtonMouseClickHandler(MouseEvent event) {
    }
    
}
