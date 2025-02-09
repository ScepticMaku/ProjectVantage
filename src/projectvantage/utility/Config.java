/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.sql.*;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Mark
 */
public class Config {
    
    public void showErrorMessage(String errorMessage, String errorType) {
        JOptionPane.showMessageDialog(null, errorMessage, errorType, JOptionPane.ERROR_MESSAGE);
    }
    
    /*public void setCenterAlignment(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;
        stage.setX(centerX);
        stage.setY(centerY);
    }*/
    
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
        Database db = new Database();
        
        try(ResultSet result = db.getData("SELECT " + column + " FROM user")) {    
            while(result.next()) {
                if(value.equals(result.getString(column)))
                    return true;
            }
        }
        return false;
    }
}
