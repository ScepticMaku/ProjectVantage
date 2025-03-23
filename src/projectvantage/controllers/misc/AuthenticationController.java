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

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
//    private String username;
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
        
        emailPlaceholder.setText(email);
        secretKey = getSecretKey(email);
    }
    
    public String getSecretKey(String email) {
         try(ResultSet result = db.getData("SELECT secret_key FROM user WHERE email = '" + email + "'")) {
            if(result.next())
                return result.getString("secret_key");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
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
        
        profileController.loadChangePasswordPage(getClass(), event, targetFXML);
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
