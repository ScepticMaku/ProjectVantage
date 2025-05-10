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
    
    public void loginLog(boolean isUserLoggedIn, int userId, String description) {
        action = "Login Success";
        
        if(!isUserLoggedIn) {
            action = "Login Failed";
        }
        
        insertLog(userId, action, description);
    }
    
    public void registerLog(int userId) {
        action = "Register";
        description = "User successully registered.";
        
        insertLog(userId, action, description);
    }
    
    public void logLogout(int userId) {
        action = "Logout";
        description = "User logged out";
        
        insertLog(userId, action, description);
    }
    
    public void logResetPassword(int userId) {
        action = "Password Changed/Reset";
        description = "User changed password";
        
        insertLog(userId, action, description);
    }
    
    public void logEditProfile(int userId, String description) {
        action = "Profile Edit";
        
        insertLog(userId, action, description);
    }
    
    public void logAddUser(int userId, String addedUserUsername) {
        action = "Add User";
        description = "User added: " + addedUserUsername;
        
        insertLog(userId, action, description);
    }
    
    public void logAddProject(int userId, String projectName) {
        action = "Add Project";
        description = "Project added: " + projectName;
        insertLog(userId, action, description);
    }
    
    public void logEditProject(int userId, String description) {
        action = "Edit Project";
        insertLog(userId, action, description);
    }
    
    public void logDeleteProject(int userId, String projectName) {
        action = "Delete Project";
        description = "Project deleted: " + projectName;
        insertLog(userId, action, description);
    }
}
