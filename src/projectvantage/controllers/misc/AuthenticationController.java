/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.misc;

import projectvantage.utility.GoogleAuthenticationConfig;
import projectvantage.utility.Config;
import projectvantage.utility.PageConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.DatabaseConfig;
import projectvantage.controllers.authentication.LoginController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    
    LoginController loginController = LoginController.getInstance();
    GoogleAuthenticationConfig googleAuthConf = new GoogleAuthenticationConfig();
    PageConfig pageConf = new PageConfig();
    Config config = new Config();
    DatabaseConfig dbConf = new DatabaseConfig();
    dbConnect db = new dbConnect();

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
    private TextField emailField;

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

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String emailInput = emailField.getText();
        String OTP = otpField.getText();
        
        if(emailInput.isEmpty()) {
            config.showErrorMessage("Email field must not be empty", "Verification Error", currentStage);
            return;
        }
        
        if(OTP.isEmpty()) {
            config.showErrorMessage("Verification field must not be empty", "Verification Error", currentStage);
            return;
        }
        
        if(!googleAuthConf.verifyOTP(currentStage, OTP, secretKey)) {
            config.showErrorMessage("Incorrect verification code.", "Verification Error", currentStage);
            return;
        }
        
        config.showAlert(Alert.AlertType.INFORMATION, "Verification Alert", "Account successfully verified!", currentStage);
        
        loginController.switchScene(getClass(), event, targetFXML);
        currentStage.setTitle(title);
    }
    
}
