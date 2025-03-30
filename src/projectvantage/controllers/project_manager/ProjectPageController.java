/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.project_manager;

import projectvantage.utility.dbConnect;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class ProjectPageController implements Initializable {
    
    dbConnect db = new dbConnect();
    
    private static final int ROWS_PER_PAGE = 5;
    

    @FXML
    private Pagination pagination;
    @FXML
    private TableView<?> userTable;
    @FXML
    private TableColumn<?, ?> idColumn;
    @FXML
    private TableColumn<?, ?> nameColumn;
    @FXML
    private TableColumn<?, ?> duedateColumn;
    @FXML
    private TableColumn<?, ?> statusColumn;
    @FXML
    private TableColumn<?, ?> actionColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void userTableMouseClickHandler(MouseEvent event) {
    }
}
