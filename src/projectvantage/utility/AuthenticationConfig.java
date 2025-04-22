/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
    
    public void rememberUser(String username) {
        try (FileWriter writer = new FileWriter("rememberMe.txt")) {
            writer.write(username);
        } catch (Exception e) {
            System.out.println("There was a problem creating the text file: " + e.getMessage());
        }
    }
    
    public String getRememberedUser() {
        File file = new File("rememberMe.txt");
        
        if(file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return reader.readLine();
            } catch (Exception e) {
                System.out.println("There was a problem with the text file: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public void logout() {
        File file = new File("rememberMe.txt");
        if(file.exists()) {
            file.delete();
        }
    }
}
