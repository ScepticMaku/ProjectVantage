/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

/**
 *
 * @author SCC8
 */
public class LogConfig {
    
    dbConnect db = new dbConnect();
    
    private String action;
    private String description;
    
    private void insertLog(int userId, String act, String desc) {
        String sql = "INSERT INTO system_log (user_id, action, description) VALUES (?, ?, ?)";
        
        db.executeQuery(sql, userId, act, desc);
    }
    
    public void logLogin(boolean isUserLoggedIn, int userId, String desc) {
        
        this.action = "Login Success";
        this.description = desc;
        
        if(!isUserLoggedIn) {
            this.action = "Login Failed";
        }
        
        insertLog(userId, action, description);
    }
    
}
