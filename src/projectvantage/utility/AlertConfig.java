 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.util.Optional;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

/**
 *
 * @author Mark Work Account
 */
public class AlertConfig {
    
    private static final String ALERT_STYLE_CSS = "/projectvantage/css/alert-style.css";
    
    public void showAlert(Alert.AlertType alertType, String headerTitle, String message,Stage stage) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerTitle);
        alert.setContentText(message);
        alert.initOwner(stage);
         
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource(ALERT_STYLE_CSS).toExternalForm());
        dialogPane.getStyleClass().add("alert");
        
        alert.showAndWait();
    }
    
    public void showLogoutConfirmationAlert(Node node, Event event, int userId) {
        PageConfig pageConf = new PageConfig();
        AuthenticationConfig authConf = new AuthenticationConfig();
        LogConfig logConf = new LogConfig();
        
        Stage currentStage = (Stage) node.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Log out Confirmation.");
        alert.setContentText("Are you sure you want to log out?");
        alert.initOwner(currentStage);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource(ALERT_STYLE_CSS).toExternalForm());
        dialogPane.getStyleClass().add("alert");
        
        String loginFXML = "/projectvantage/fxml/authentication/Login.fxml";
        
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK)
                try {
                    authConf.logout();
                    pageConf.switchScene(getClass(), event, loginFXML);
                    logConf.logLogout(userId);
                    
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public void showExitConfirmationAlert(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exit Confirmation");
        alert.setContentText("Are you sure you want to exit?");
        alert.initOwner(stage);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource(ALERT_STYLE_CSS).toExternalForm());
        dialogPane.getStyleClass().add("alert");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            stage.close();
        }
    }
    
    public void showDeleteConfirmationAlert(Stage stage, String query, int id) {
        dbConnect db = new dbConnect();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete?");
        alert.initOwner(stage);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource(ALERT_STYLE_CSS).toExternalForm());
        dialogPane.getStyleClass().add("alert");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK && db.executeQuery(query, id)) {
            System.out.println("Deleted successfully!");
            showAlert(Alert.AlertType.INFORMATION, "Successfully deleted!", "Deletion Successful", stage);
        }
    }
    
    public void showWindowLoadErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Window Load Error", errorMessage, stage);
    }
    
    public void showLoginErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Login Error", errorMessage, stage);
    }
    
    public void showRegisterErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Register Error", errorMessage, stage);
    }
    
    public void showAuthenticationErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Authentication Error", errorMessage, stage);
    }
    
    public void showDatabaseErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Database Error", errorMessage, stage);
    }
    
    public void showEditEUserErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Edit User Error", errorMessage, stage);
    }
    
    public void showEditProfileErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Edit User Error", errorMessage, stage);
    }
    
    public void showChangePasswordErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Change Password Error", errorMessage, stage);
    }
    
    public void showResetPasswordErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Reset Password Error", errorMessage, stage);
    }
    
    public void showEditProjectErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Edit Project Error", errorMessage, stage);
    }
    
    public void showAddProjectErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Add Project Error", errorMessage, stage);
    }
    
    public void showAddTeamErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Add Team Error", errorMessage, stage);
    }
    
    public void showEditTeamErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Edit Team Error", errorMessage, stage);
    }
    
    public void showAddTeamMemberErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Add Team Member Error", errorMessage, stage);
    }
    
    public void showAddTaskErrorAlert(Stage stage, String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Add Task Error", errorMessage, stage);
    }
}
