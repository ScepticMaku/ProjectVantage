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
public class ProjectActivity {
    
    private final String date;
    private final String project;
    private final String activity;
    
    public ProjectActivity(String date, String project, String activity) {
        this.date = date;
        this.project = project;
        this.activity = activity;
    }
    
    public String getDate() {
        return date;
    }
    
    public String getProject() {
        return project;
    }
    
    public String getActivity() {
        return activity;
    }
    
}
