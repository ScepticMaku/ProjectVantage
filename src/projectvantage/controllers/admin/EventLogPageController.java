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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class EventLogPageController implements Initializable {
    
    ObservableList<String> eventLogs = FXCollections.observableArrayList();
    dbConnect db = new dbConnect();
    @FXML
    private ListView<String> listView;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadLogs();
    }    
    
    private void loadLogs() {
        String sql = "SELECT system_log.id AS id, user.username AS username, description, timestamp "
                + "FROM system_log INNER JOIN user ON system_log.user_id = user.id ORDER BY id DESC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                String log = "[ID]: " + result.getInt("id") + " - [TIMESTAMP]: " + result.getTimestamp("timestamp") + " - [USER]: " + result.getString("username") + ": " + result.getString("description");
                eventLogs.add(log);
            }
            listView.setItems(eventLogs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
