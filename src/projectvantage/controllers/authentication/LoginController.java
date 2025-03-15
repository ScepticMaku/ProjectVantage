/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.authentication;

import projectvantage.utility.Config;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.PageConfig;
import projectvantage.controllers.team_member.TeamMemberMainPageController;
import projectvantage.controllers.admin.AdminPageController;
import projectvantage.utility.AuthenticationConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import projectvantage.utility.dbConnect;
import java.sql.*;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class LoginController implements Initializable {
    
    Config config = new Config();
    PageConfig pageConf = new PageConfig();
    ElementConfig elementConf = new ElementConfig();
    AuthenticationConfig authConfig = new AuthenticationConfig();
    AdminPageController adminController = AdminPageController.getInstance();
    
    private static LoginController instance;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private Pane titlePane;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Rectangle exitButtonBG;
    @FXML
    private ImageView closeButton;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }
    
    public static LoginController getInstance() {
        return instance;
    }
    
    private boolean locateUser(String user) {
        dbConnect db = new dbConnect();
        try(ResultSet result = db.getData("SELECT username, password FROM user  WHERE username = '" + user + "'")) {
            return result.next();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }
    
    private String getPassword(String user) {
        dbConnect db = new dbConnect();
        try(ResultSet result = db.getData("SELECT password FROM user  WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    public String getRole(String user) {
        dbConnect db = new dbConnect();
        try(ResultSet result = db.getData("SELECT role FROM user WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("role");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    public String getStatus(String user) {
        dbConnect db = new dbConnect();
        try(ResultSet result = db.getData("SELECT status FROM user WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("status");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    public String getSalt(String user) {
        dbConnect db = new dbConnect();
        try(ResultSet result = db.getData("SELECT salt FROM user WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("salt");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    private void loginUser(Event event) throws Exception {
        Stage currentStage = (Stage)loginButton.getScene().getWindow();
        
        String username = usernameField.getText();
        String password = passwordField.getText();
        String userRole = getRole(username);
        String userPassword = getPassword(username);
        String salt = getSalt(username);

        
        if(username.isEmpty()) {
            config.showErrorMessage("Username must not be empty.", "Login error", currentStage);
            return;
        }
        
        if (password.isEmpty()) {
            config.showErrorMessage("Password must not be empty.", "Login error", currentStage);
            return;
        }
        
        boolean isUsernameFound = locateUser(username);
        
        if(!isUsernameFound) {
            config.showErrorMessage("Username not found.", "Login error", currentStage);
            return;
        }
        
        boolean doesPasswordMatch = authConfig.verifyPassword(password, userPassword, salt);
        
        if(!doesPasswordMatch) {
            config.showErrorMessage("Password does not match.", "Login Error", currentStage);
            return;
        }
        
        boolean isStatusInactive = getStatus(username).equals("inactive");
        
        if(isStatusInactive) {
            config.showErrorMessage("Your account isn't active yet.", "Account Status Error", currentStage);
            return;
        }
        
        config.showAlert(Alert.AlertType.INFORMATION, "Login Message.", "Successfully Logged In", currentStage);
        
        switch(userRole) {
            case "team member": 
                switchScene(getClass(), event, "/projectvantage/fxml/team_member/TeamMemberMainPage.fxml");
                break;
            case "admin":
                switchScene(getClass(), event, "/projectvantage/fxml/admin/AdminPage.fxml");
                break;
            default:
                config.showErrorMessage("Role not found", "Role error", currentStage);
        }
    }
    
    /*
    public void loginToDashboard(Class getClass, Event evt, String targetFXML, String dashboardFXML) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass.getResource(targetFXML));
        FXMLLoader dashboardLoader = new FXMLLoader(getClass.getResource(dashboardFXML));
        Parent root = loader.load();
        Parent dashboardRoot = dashboardLoader.load();
        
        Stage currentStage = (Stage) titlePane.getScene().getWindow();
        
        String user = usernameField.getText();
        String pass = passwordField.getText();
        
        switch(getRole(user, pass)){
            case "team member":
                TeamMemberMainPageController teamMemberController = loader.getController();
                teamMemberController.setUsername(user);
            break;
            case "admin":
                AdminPageController adminController = loader.getController();
                AdminDashboardPageController dashboardController = dashboardLoader.getController();
                adminController.setUsername(user);
                dashboardController.loadUsername(user);
            break;
        }
        
        Stage stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        pageConf.setCenterAlignment(stage);
        stage.show();
        config.showAlert(Alert.AlertType.INFORMATION, "Login Sucessful!", "Welcome " + user + "!",currentStage);
    }*/
    
    public void switchScene(Class getClass, Event evt, String targetFXML) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass.getResource(targetFXML));
        Parent root = loader.load();
        
        Stage currentStage = (Stage) titlePane.getScene().getWindow();
        
        String user = usernameField.getText();
        String userRole = getRole(user);
        
        switch(userRole){
            case "team member":
                TeamMemberMainPageController teamMemberController = loader.getController();
                teamMemberController.setUsername(user);
                pageConf.loadDashboardPage("/projectvantage/fxml/team_member/TeamMemberDashboardPage.fxml", user, teamMemberController.getBackgroundPane(), teamMemberController.getRootPane());
            break;
            case "admin":
                AdminPageController adminController = loader.getController();
                adminController.setUsername(user);
                pageConf.loadDashboardPage("/projectvantage/fxml/admin/AdminDashboardPage.fxml", user, adminController.getBackgroundPane(), adminController.getRootPane());
            break;
        }
        
        Stage stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        pageConf.setCenterAlignment(stage);
        stage.show();
        config.showAlert(Alert.AlertType.INFORMATION, "Login Sucessful!", "Welcome " + user + "!",currentStage);
    }
    
    private boolean isEnterPressed(KeyEvent event) throws Exception {
        return event.getCode() == KeyCode.ENTER;
    }
    
    @FXML
    private void registerButtonMouseClickHandler(MouseEvent event) throws Exception {
        String FXML = "/projectvantage/fxml/authentication/Register.fxml";
        pageConf.switchScene(getClass(), event, FXML);
    }

    @FXML
    private void loginButtonMouseClickHandler(MouseEvent event) throws Exception {
        loginUser(event);
    }

    @FXML
    private void registerButtonMouseExitHandler(MouseEvent event) {
        registerButton.setStyle("-fx-text-fill: #0593ff");
    }

    @FXML
    private void registerButtonMouseEnterHandler(MouseEvent event) {
        registerButton.setStyle("-fx-text-fill: #0676c6");
    }

    @FXML
    private void registerButtonMousePressHandler(MouseEvent event) {
        registerButton.setStyle("-fx-text-fill: #01528d");
    }

    @FXML
    private void usernameFieldOnKeyPressedHandler(KeyEvent event) throws Exception {
        if(isEnterPressed(event))
            loginUser(event);
    }

    @FXML
    private void registerFieldOnKeyPressedHandler(KeyEvent event) throws Exception{
        if(isEnterPressed(event))
            loginUser(event);
    }

    @FXML
    private void closeButtonMouseReleaseHandler(MouseEvent event) {
        exitButtonBG.setFill(Color.web("#d71515"));
    }

    @FXML
    private void closeButtonMouseExitHandler(MouseEvent event) {
        elementConf.fadeOut(exitButtonBG);
    }

    @FXML
    private void closeButtonMouseEnterHandler(MouseEvent event) {
        elementConf.fadeIn(exitButtonBG);
    }

    @FXML
    private void closeButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        config.showAlert(Alert.AlertType.CONFIRMATION, "Exit Confirmtaion.", "Do you want to exit?", currentStage);
    }

    @FXML
    private void closeButtonMousePressHandler(MouseEvent event) {
        exitButtonBG.setFill(Color.web("#971111"));
    }
    
}
