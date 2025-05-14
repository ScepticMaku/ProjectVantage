/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.models;

import java.time.LocalDate;

/**
 *
 * @author Markj
 */
public class TeamActivity {
    
    private String date;
    private String team;
    private String action;
    
    public TeamActivity(String date, String team, String action) {
        this.date = date;
        this.team = team;
        this.action = action;
    }
    
    public String getDate() {
        return date;
    }
    
    public String getTeam() {
        return team;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public void setTeam(String team) {
        this.team = team;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
}
