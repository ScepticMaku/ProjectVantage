
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Mark
 */
public class Config {
    
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_NUMBER_REGEX = "^(09\\d{9}|\\+639\\d{9})$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
    
    public void showErrorMessage(String errorMessage, String errorType, Stage owner) {
        showAlert(Alert.AlertType.ERROR, errorType, errorMessage, owner);
    }
    
    public void showExitConfirmationAlert(Alert alert) {
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
                System.exit(0);
        });
    }
    
    public void showAlert(Alert.AlertType alertType, String header, String message, Stage owner) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(message);
//        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(owner); 
         
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/projectvantage/css/alert-style.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");
        
        if(alertType == AlertType.CONFIRMATION && header.contains("Exit")) {
            showExitConfirmationAlert(alert);
            return;
        }
        
        alert.showAndWait();
    }
    
    public boolean isValidPhoneNumber(String phoneNumber) {
        boolean hasAlpha = false;
        
        for(char c : phoneNumber.toCharArray()) {
            if(Character.isAlphabetic(c)) {
                hasAlpha = true;
            }
        }
        return hasAlpha;
    }
    
    public boolean isValidPhoneNumberFormat(String phoneNumber) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }
    
    public boolean isValidEmailFormat(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
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
