/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import projectvantage.controllers.misc.ProfilePageController;
import projectvantage.controllers.admin.AdminDashboardPageController;
import projectvantage.controllers.team_member.TeamMemberDashboardPageController;
import projectvantage.controllers.admin.AdminUserPageController;
import projectvantage.controllers.admin.EditUserPageController;
import projectvantage.controllers.authentication.GoogleAuthenticationController;
import projectvantage.controllers.misc.EditProfilePageController;
import projectvantage.models.User;


import java.sql.ResultSet;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import projectvantage.controllers.misc.AuthenticationController;
import projectvantage.controllers.misc.ResetPasswordPageController;

/**
 *
 * @author Mark Work Account
 */
public class PageConfig {
    
    Config config = new Config();
    AlertConfig alertConf = new AlertConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    
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
        stage.setResizable(false);
        setCenterAlignment(stage);
        stage.show();
    }
    
    public void loadProfilePage(String targetFXML, String user, Node node, BorderPane pane) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(targetFXML));
        Parent root = loader.load();
        ProfilePageController.getInstance().loadUser(user);
        pane.setCenter(root);
    }
    
    public void loadUserPage(String targetFXML, String user, Node node, BorderPane pane) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(targetFXML));
        Parent root = loader.load();
        AdminUserPageController.getInstance().loadUser(user);
        pane.setCenter(root);
    }
    
    public void loadEditUserPage(String targetFXML, String user, Node node, BorderPane pane) {
        dbConnect db = new dbConnect();
        Stage currentStage = (Stage) node.getScene().getWindow();
        
        String sql = "SELECT first_name, middle_name, last_name, email, phone_number, username, role, status FROM user WHERE username='" + user + "'";
        try{
            ResultSet result = db.getData(sql);
            if(result.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(targetFXML));
                Parent root = loader.load();
                
                String first_name = result.getString("first_name");
                String middle_name = result.getString("middle_name");
                String last_name = result.getString("last_name");
                String email = result.getString("email");
                String phone_number = result.getString("phone_number");
                String role = result.getString("role");
                String status = result.getString("status");
                
                EditUserPageController.getInstance().loadUserContents(first_name, middle_name, last_name, email, phone_number, user, role, status);
                pane.setCenter(root);   
            }
            result.close();
        } catch (Exception e) {
            alertConf.showDatabaseErrorAlert(currentStage, e.getMessage());
        }
    }
    
    public void loadDashboardPage(Stage stage, String targetFXML, String username, Node node, BorderPane pane) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(targetFXML));
        Parent root = loader.load();
        
        User user = dbConf.getUserByUsername(username);
        
        switch(user.getRole()) {
            case "team member":
                TeamMemberDashboardPageController.getInstance().loadUsername(username);
                break;
            case "admin":
                AdminDashboardPageController.getInstance().loadUsername(username);
            break;
        }
        
        stage.setTitle("Dashboard");
        pane.setCenter(root);
    }
    
    public void loadEditProfilePage(String targetFXML, BorderPane pane, String username) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(targetFXML));
        Parent root = loader.load();
        EditProfilePageController.getInstance().loadUserContents(username);
        pane.setCenter(root);
    }
    
     public void switchToVerifyAuthenticator(MouseEvent event, Class getClass, Node node, String sql, String username) throws Exception {
        Stage currentStage = (Stage)node.getScene().getWindow();
        String FXML = "/projectvantage/fxml/authentication/GoogleAuthentication.fxml";
        Parent root = FXMLLoader.load(getClass.getResource(FXML));
        
        currentStage.setScene(new Scene(root));
        currentStage.setResizable(false);
        setCenterAlignment(currentStage);
        
        GoogleAuthenticationController.getInstance().loadContent(sql, username);
        
        currentStage.setTitle("Google Authentication");
        currentStage.show();
    }
     
    public void switchToAuthenticator(Event event, Class getClass, Node node, String targetFXML, String title, String email) throws Exception {
        Stage currentStage = (Stage)node.getScene().getWindow();
        
        String FXML = "/projectvantage/fxml/misc/Authentication.fxml";
        Parent root = FXMLLoader.load(getClass.getResource(FXML));
        
        currentStage.setScene(new Scene(root));
        currentStage.setResizable(false);
        setCenterAlignment(currentStage);
        
        AuthenticationController.getInstance().loadContent(targetFXML, title, email);
        
        currentStage.setTitle("Google Authentication");
        currentStage.show();
    }
    
    public void switchToResetPassword(Class getClass, Node node, String FXML, String username) throws Exception {
        Parent root = FXMLLoader.load(getClass.getResource(FXML));
        Stage stage = (Stage)node.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        setCenterAlignment(stage);
        
        ResetPasswordPageController.getInstance().setUsername(username);
        
        stage.show();
    }
}
