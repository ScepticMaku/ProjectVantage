/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.main;

import projectvantage.utility.PageConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.DatabaseConfig;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Mark
 */
public class ProjectVantage extends Application {
    
    DatabaseConfig dbConf = new DatabaseConfig();
    PageConfig pageConf = new PageConfig();
    AlertConfig alertConf = new AlertConfig();
    
    
    private static ProjectVantage instance;
    private static Stage primaryStage;
    
    String loginFXML = "/projectvantage/fxml/authentication/Login.fxml";
    
    public static ProjectVantage getInstance() {
        return instance;
    }
    
    public Stage getStage() {
        return primaryStage;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
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
