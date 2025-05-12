/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.main;

import projectvantage.utility.PageConfig;
import projectvantage.utility.SessionConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.AuthenticationConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.LogConfig;
import projectvantage.controllers.authentication.LoginController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import projectvantage.models.User;

/**
 *
 * @author Mark
 */
public class ProjectVantage extends Application {
    
    DatabaseConfig dbConf = new DatabaseConfig();
    PageConfig pageConf = new PageConfig();
    AlertConfig alertConf = new AlertConfig();
    LoginController loginControl = new LoginController();
    AuthenticationConfig authConf = new AuthenticationConfig();
    LogConfig logConf = new LogConfig();
    
    private static ProjectVantage instance;
    private static Stage primaryStage;
    
    private String role;
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String secretKey;
    
    String loginFXML = "/projectvantage/fxml/authentication/Login.fxml";
    
    public static ProjectVantage getInstance() {
        return instance;
    }
    
    public Stage getStage() {
        return primaryStage;
    }
    
    private void checkRole(Stage stage, String userInput, String role, int userId) throws Exception {
        switch(role) {
            case "admin":
                switchScene(stage, userInput, getClass(), "/projectvantage/fxml/admin/AdminPage.fxml");
                break;
            case "standard":
                switchScene(stage, userInput, getClass(), "/projectvantage/fxml/team_member/TeamMemberMainPage.fxml");
                break;
            case "project manager":
                switchScene(stage, userInput, getClass(), "/projectvantage/fxml/project_manager/ProjectManagerPage.fxml");
                break;
            case "team manager":
                switchScene(stage, userInput, getClass(), "/projectvantage/fxml/team_manager/TeamManagerPage.fxml");
                break;
            default:
                alertConf.showLoginErrorAlert(stage, "Role not found.");
                logConf.loginLog(false, userId, "Role not found.");
        }
    }
    
    private void switchScene(Stage stage, String userInput, Class getClass, String targetFXML) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass.getResource(targetFXML));
        Parent root = loader.load();
        
        authConf.rememberUser(userInput);
        
        stage.getIcons().add(new Image("/projectvantage/resources/img/ProjectLogo.png"));
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        
        stage.setOnCloseRequest(event -> {
            event.consume();
            alertConf.showExitConfirmationAlert(stage);
        });
        
        stage.show();
        pageConf.setCenterAlignment(stage);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        String rememberedUser = authConf.getRememberedUser();
        SessionConfig sessionConf = new SessionConfig();
        
        if(rememberedUser != null) {
            User user = dbConf.getUserByUsername(rememberedUser);
            
            role = user.getRole();
            firstName = user.getFirstName();
            id = user.getId();
            middleName = user.getMiddleName();
            lastName = user.getLastName();
            email = user.getEmail();
            phoneNumber = user.getPhoneNumber();
            username = user.getUsername();
            secretKey = user.getSecretKey();
            
            sessionConf.setSession(id, firstName, middleName, lastName, email, phoneNumber, username, role);
            
            if(secretKey != null) {
                sessionConf.setSecretKey(secretKey);
            }
            
            checkRole(primaryStage, rememberedUser, role, id);
            return;
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(loginFXML));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.getIcons().add(new Image("/projectvantage/resources/img/ProjectLogo.png"));

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            alertConf.showExitConfirmationAlert(primaryStage);
        });

        primaryStage.show();
        pageConf.setCenterAlignment(primaryStage); 
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
