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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Mark
 */
public class Config {
    
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_NUMBER_REGEX = "^9\\d{9}$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
    
        public void setCenterAlignment(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;
        stage.setX(centerX);
        stage.setY(centerY);
    }
    
    public void switchScene(Class getClass, Event evt, String targetFXML) throws Exception {
        Parent root = FXMLLoader.load(getClass.getResource(targetFXML));
        Stage stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
        setCenterAlignment(stage);
    }
    
    public void showErrorMessage(String errorMessage, String errorType, Stage owner) {
//        JOptionPane.showMessageDialog(null, errorMessage, errorType, JOptionPane.ERROR_MESSAGE);
        showAlert(Alert.AlertType.ERROR, errorType, errorMessage, owner);
    }
    
    public void showConfirmationAlert(Alert alert) {
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
                System.exit(0);
        });
    }
    
    public void showAlert(Alert.AlertType alertType, String title, String message, Stage owner) {
        Alert alert = new Alert(alertType); 
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);

        // Show the alert and manually center it
        alert.setOnShown(e -> {
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.setX(owner.getX() + (owner.getWidth() - alertStage.getWidth()) / 2);
            alertStage.setY(owner.getY() + (owner.getHeight() - alertStage.getHeight()) / 2);
        });

        if(alertType == AlertType.CONFIRMATION) {
            showConfirmationAlert(alert);
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
