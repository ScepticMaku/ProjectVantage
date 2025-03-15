/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * @author Mark Work Account
 */
public class AuthenticationConfig {
    
    public String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    public String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest((password + salt).getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for(byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("SHA-256 algorithm not found!", e);
        }
    }
    
    public boolean verifyPassword(String enteredPassword, String storedHash, String storedSalt) {
        String newHash = hashPassword(enteredPassword, storedSalt);
        return newHash.equals(storedHash);
    }
}
