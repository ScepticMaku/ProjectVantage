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
public class Team {
    private int id;
    private String name;
    private int project_id;
    
    public Team(int id, String name, int project_id) {
        this.id = id;
        this.name = name;
        this.project_id = project_id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getProjectId() {
        return project_id;
    }
}
