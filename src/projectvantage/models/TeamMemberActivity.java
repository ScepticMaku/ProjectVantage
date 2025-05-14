/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.models;

/**
 *
 * @author markjay
 */
public class TeamMemberActivity {
    
    private String date;
    private String task;
    private String project;
    
    public TeamMemberActivity(String date, String task, String project) {
        this.date = date;
        this.task = task;
        this.project = project;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public void setTask(String task) {
        this.task = task;
    }
    
    public void setProject(String project) {
        this.project = project;
    }
    
    public String getDate() {
        return date;
    }
    
    public String getTask() {
        return task;
    }
    
    public String getProject() {
        return project;
    }
}
