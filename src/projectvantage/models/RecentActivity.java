/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.models;

/**
 *
 * @author Markj
 */
public class RecentActivity {
    
    private final String date;
    private final String user;
    private final String action;
    private final String project;

    public RecentActivity(String date, String user, String action) {
        this(date, user, action, null);
    }

    public RecentActivity(String date, String user, String action, String project) {
        this.date = date;
        this.user = user;
        this.action = action;
        this.project = project;
    }

    public String getDate() { 
        return date; 
    }
    
    public String getUser() {
        return user; 
    }
    
    public String getAction() { 
        return action; 
    }
    
    public String getProject() {
        return project;
    }
}
