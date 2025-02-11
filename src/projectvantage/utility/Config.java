/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.sql.*;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Mark
 */
public class Config {
    
    public void switchScene(Class getClass, Event evt, String targetFXML) throws Exception {
        Parent root = FXMLLoader.load(getClass.getResource(targetFXML));
        Stage stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
        setCenterAlignment(stage);
    }
    
    public void showErrorMessage(String errorMessage, String errorType) {
//        JOptionPane.showMessageDialog(null, errorMessage, errorType, JOptionPane.ERROR_MESSAGE);
        showAlert(Alert.AlertType.ERROR, errorType, errorMessage);


    }
    
    public void setCenterAlignment(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;
        stage.setX(centerX);
        stage.setY(centerY);
    }
    
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType); 
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public boolean checkPhoneNumber(String phoneNumber) {
        boolean hasAlpha = false;
        
        for(char c : phoneNumber.toCharArray()) {
            if(Character.isAlphabetic(c)) {
                hasAlpha = true;
            }
        }
        return hasAlpha;
    }
    
    public boolean isDuplicated(String column, String value) throws SQLException {
        dbConnect db = new dbConnect();
        
        try(ResultSet result = db.getData("SELECT " + column + " FROM user")) {    
            while(result.next()) {
                if(value.equals(result.getString(column)))
                    return true;
            }
        }
        return false;
    }
    
    
}
