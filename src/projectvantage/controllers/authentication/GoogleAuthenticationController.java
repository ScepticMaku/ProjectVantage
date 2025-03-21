/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.authentication;

import projectvantage.utility.Config;
import projectvantage.utility.PageConfig;
import projectvantage.utility.GoogleAuthenticationConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.AlertConfig;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
public class GoogleAuthenticationController implements Initializable {
    
    private static GoogleAuthenticationController instance;
    
    dbConnect connect = new dbConnect();
    PageConfig pageConf = new PageConfig();
    Config config = new Config();
    GoogleAuthenticationConfig googleAuthConf = new GoogleAuthenticationConfig();
    AlertConfig alertConf = new AlertConfig();
    
    private String query;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String salt;
    private String hashedPassword;
    private String secretKey;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView qrImageView;
    @FXML
    private Button submitButton;
    @FXML
    private TextField otpField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }
    
    public static GoogleAuthenticationController getInstance() {
        return instance;
    }
    
    public void loadContent(String...info) {
        
        query = info[0];
        firstName = info[1];
        middleName = info[2];
        lastName = info[3];
        email = info[4];
        phoneNumber = info[5];
        username = info[6];
        salt = info[7];
        hashedPassword = info[8];
        
        secretKey = googleAuthConf.generateSecretKey();
        String issuer = "ProjectVantage";
        
        googleAuthConf.generateQRCode(secretKey, email, issuer, qrImageView);
    }
    
    private boolean isEnterPressed(KeyEvent event) throws Exception {
        return event.getCode() == KeyCode.ENTER;
    }
    
    private void registerUser(Event event) throws Exception {
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
        
        if(connect.insertData(query, firstName, middleName, lastName, email, phoneNumber, username, salt, hashedPassword, secretKey)) {
             System.out.println("User added to database!");
             alertConf.showAlert(Alert.AlertType.INFORMATION, "User successfully registered!", "Register Completed!", currentStage);
         }
        
        String loginFXML = "/projectvantage/fxml/authentication/Login.fxml";
        
        pageConf.switchScene(getClass(), event, loginFXML);
        currentStage.setTitle("Login");
    }

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) throws Exception {
        registerUser(event);
    }

    @FXML
    private void otpFieldKeyPressedHandler(KeyEvent event) throws Exception {
        if(isEnterPressed(event))
            registerUser(event);
    }
}
