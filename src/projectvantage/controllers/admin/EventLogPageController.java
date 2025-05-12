/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.utility.dbConnect;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class EventLogPageController implements Initializable {
    
    ObservableList<String> eventLogs = FXCollections.observableArrayList();
    FilteredList<String> filteredData = new FilteredList<>(eventLogs, s -> true);
    
    dbConnect db = new dbConnect();
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField searchField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadLogs();
        
        Platform.runLater(() -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                String keyword = newValue.toLowerCase();
                
                filteredData.setPredicate(item -> {
                    if(keyword == null || keyword.isEmpty()) {
                        return true;
                    }
                    return item.toLowerCase().contains(keyword);
                });
            });
        });
    }    
    
    private void loadLogs() {
        String sql = "SELECT system_log.id AS id, user.username AS username, description, timestamp "
                + "FROM system_log INNER JOIN user ON system_log.user_id = user.id ORDER BY id DESC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                String log = "[ID]: " + result.getInt("id") + " - [TIMESTAMP]: " + result.getTimestamp("timestamp") + " - [USER]: " + result.getString("username") + ": " + result.getString("description");
                eventLogs.add(log);
            }
            listView.setItems(filteredData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
