/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Mark Work Account
 */
public class GoogleAuthenticationConfig {
    
    Config config = new Config();
    AlertConfig alertConf = new AlertConfig();
    
    private static final int QR_CODE_WIDTH = 200;
    private static final int QR_CODE_HEIGHT = 200;
    
    public String generateSecretKey() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }
    
    public void generateQRCode(String secret, String user, String issuer, ImageView imageView) {
        try {
            String otpAuthUrl = String.format(
                    "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, user, secret, issuer
            );
            
            BitMatrix matrix = new MultiFormatWriter().encode(otpAuthUrl, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
            
            Image fxImage = SwingFXUtils.toFXImage(qrImage, null);
            imageView.setImage(fxImage);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean verifyOTP(Stage stage, String otpText, String secretKey) {
        try{
            int otpCode = Integer.parseInt(otpText);
            GoogleAuthenticator gAuth = new GoogleAuthenticator();
            boolean isValid = gAuth.authorize(secretKey, otpCode);
            
            return isValid;
            
        } catch(Exception e) {
            alertConf.showAuthenticationErrorAlert(stage, "You must enter enter only numbers.");
            return false;
        }
    }
}
