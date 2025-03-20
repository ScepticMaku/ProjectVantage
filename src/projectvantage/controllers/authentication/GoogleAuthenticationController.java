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


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        String OTP = otpField.getText();
        
        if(OTP.isEmpty()) {
            config.showErrorMessage("Verification field must not be empty", "Verification Error", currentStage);
            return;
        }
        
        if(!googleAuthConf.verifyOTP(currentStage, OTP, secretKey)) {
            config.showErrorMessage("Incorrect verification code.", "Verification Error", currentStage);
            return;
        }
        
        config.showAlert(Alert.AlertType.INFORMATION, "Verification Alert", "Account successfully verified!", currentStage);
        
        if(connect.insertData(query, firstName, middleName, lastName, email, phoneNumber, username, salt, hashedPassword, secretKey)) {
             System.out.println("User added to database!");
             config.showAlert(Alert.AlertType.INFORMATION, "User successfully registered!", "Register Completed!", currentStage);
         }
        
        String FXML = "/projectvantage/fxml/authentication/Login.fxml";
        pageConf.switchScene(getClass(), event, FXML);
        currentStage.setTitle("Login");
    }
}
