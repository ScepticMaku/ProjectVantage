
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Mark
 */
public class Config {
    
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_NUMBER_REGEX = "^(09\\d{9}|\\+639\\d{9})$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
    
    public boolean isValidPhoneNumber(String phoneNumber) {
        boolean hasAlpha = false;
        
        for(char c : phoneNumber.toCharArray()) {
            if(Character.isAlphabetic(c)) {
                hasAlpha = true;
            }
        }
        return hasAlpha;
    }
    
    public boolean isValidPhoneNumberFormat(String phoneNumber) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }
    
    public boolean isValidEmailFormat(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    
    public boolean isDuplicated(String column, String value) throws SQLException {
        dbConnect db = new dbConnect();
        
        try(ResultSet result = db.getData("SELECT " + column + " FROM user")) {    
            while(result.next()) {
                if(value.equals(result.getString(column)))
                    return true;
            }
        }
        return false;
    }
}
