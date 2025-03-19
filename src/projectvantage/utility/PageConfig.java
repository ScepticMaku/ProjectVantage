/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import projectvantage.controllers.misc.ProfilePageController;
import projectvantage.controllers.admin.AdminDashboardPageController;
import projectvantage.controllers.authentication.LoginController;
import projectvantage.controllers.team_member.TeamMemberDashboardPageController;
import projectvantage.controllers.admin.AdminUserPageController;
import projectvantage.controllers.admin.EditUserPageController;

import java.sql.ResultSet;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import projectvantage.controllers.misc.EditProfilePageController;


/**
 *
 * @author Mark Work Account
 */
public class PageConfig {
    Config config = new Config();
    
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
    
    public void loadProfilePage(String targetFXML, String user, Node node, BorderPane pane) {
        
        dbConnect db = new dbConnect();
        Stage currentStage = (Stage) node.getScene().getWindow();
        
        String sql = "SELECT first_name, middle_name, last_name, email, phone_number, username, role FROM user WHERE username='" + user + "'";
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
                
                ProfilePageController.getInstance().loadUser(first_name, middle_name, last_name, email, phone_number, user, role);
                pane.setCenter(root);   
            }
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
            config.showErrorMessage("There was a problem with the database", "Database Error:", currentStage);
        }
    }
    
    public void loadUserPage(String targetFXML, String user, Node node, BorderPane pane) {
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
                
                AdminUserPageController.getInstance().loadUser(first_name, middle_name, last_name, email, phone_number, user, role, status);
                pane.setCenter(root);   
            }
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
            config.showErrorMessage("There was a problem with the database", "Database Error:", currentStage);
        }
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
            e.printStackTrace();
            config.showErrorMessage("There was a problem with the database", "Database Error:", currentStage);
        }
    }
    
    public void loadDashboardPage(String targetFXML, String user, Node node, BorderPane pane) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(targetFXML));
        Parent root = loader.load();
        
        
        switch(LoginController.getInstance().getRole(user)) {
            case "team member":
                TeamMemberDashboardPageController.getInstance().loadUsername(user);
                break;
            case "admin":
                AdminDashboardPageController.getInstance().loadUsername(user);
            break;
        }
        pane.setCenter(root);
    }
    
    public void loadEditProfilePage(String targetFXML, Node node, BorderPane pane, String...info) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(targetFXML));
        Parent root = loader.load();
        EditProfilePageController.getInstance().loadUserContents(info[0], info[1], info[2], info[3], info[4], info[5], info[6]);
        pane.setCenter(root);
    }
}
