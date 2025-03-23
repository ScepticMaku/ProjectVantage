/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.GoogleAuthenticationConfig;
import projectvantage.utility.Config;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class AuthenticationController implements Initializable {
    
    private static AuthenticationController instance;
    
    GoogleAuthenticationConfig googleAuthConf = new GoogleAuthenticationConfig();
    PageConfig pageConf = new PageConfig();
    Config config = new Config();
    AlertConfig alertConf = new AlertConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    dbConnect db = new dbConnect();
    ProfilePageController profileController = ProfilePageController.getInstance();

    private String secretKey;
    private String targetFXML;
    private String title;
    private String username;
    private String email;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField otpField;
    @FXML
    private Button submitButton;
    @FXML
    private Label emailPlaceholder;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        instance = this;
    }    
    
    public static AuthenticationController getInstance() {
        return instance;
    }
    
    public void loadContent(String FXML, String title, String email) {
        this.targetFXML = FXML;
        this.title = title;
        this.email = email;
        
        User user = dbConf.getUserByEmail(email);
        
        emailPlaceholder.setText(email);
        secretKey = user.getSecretKey();
        username = user.getUsername();
    }
    
    public void loadResetPasswordPage(Class getClass, Event evt, String targetFXML) throws Exception {
        Parent root = FXMLLoader.load(getClass.getResource(targetFXML));
        Stage stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        pageConf.setCenterAlignment(stage);
        
        ResetPasswordPageController.getInstance().setUsername(username);
        
        stage.show();
    }
    
    private boolean isEnterPressed(KeyEvent event) throws Exception {
        return event.getCode() == KeyCode.ENTER;
    }
    
    private void verifyUser(Event event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        String OTP = otpField.getText();
        
        if(OTP.isEmpty()) {
            alertConf.showAuthenticationErrorAlert(currentStage, "You must enter a verification code.");
            return;
        }
        
        if(!googleAuthConf.verifyOTP(currentStage, OTP, secretKey)) {
            alertConf.showAuthenticationErrorAlert(currentStage, "Incorrect verification code.");
            return;
        }
        
        alertConf.showAlert(Alert.AlertType.INFORMATION, "Authentication Successful", "Account successfully verified!", currentStage);
        
        loadResetPasswordPage(getClass(), event, targetFXML);
        currentStage.setTitle(title);
    }

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) throws Exception {
        verifyUser(event);
    }

    @FXML
    private void otpFieldKeyPressedHandler(KeyEvent event) throws Exception {
        if(isEnterPressed(event))
            verifyUser(event);
    }

    @FXML
    private void cancelButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.switchScene(getClass(), event, "/projectvantage/fxml/authentication/Login.fxml");
    }
    
}
