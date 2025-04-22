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
public class TeamMember {
    private int id;
    private int teamId;
    private String lastName;
    private String username;
    private String role;
    private String status;
    
    public TeamMember(int id, int teamId, String lastName, String username, String role, String status) {
        this.id = id;
        this.teamId = teamId;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
        this.status = status;
    }
    
    public int getId() {
        return id;
    }
    
    public int getTeamId() {
        return teamId;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getStatus() {
        return status;
    }
}
