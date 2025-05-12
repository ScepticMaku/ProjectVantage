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
public class Task {
    private int id;
    private String name;
    private String description;
    private String creationDate;
    private String dueDate;
    private String dateCompleted;
    private String creatorName;
    private int teamMemberid;
    private String assignedLastName;
    private int projectId;
    private String status;
    
    public Task(int id, String name, String description, String creationDate, String dueDate, String creatorName, int teamMemberId, int projectId, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.creatorName = creatorName;
        this.teamMemberid = teamMemberId;
        this.projectId = projectId;
        this.status = status;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getCreationDate() {
        return creationDate;
    }
    
    public String getDueDate() {
        return dueDate;
    }
    
    public String getCreatorName() {
        return creatorName;
    }
    
    public int getTeamMemberId() {
        return teamMemberid;
    }
    
    public int getProjectId() {
        return projectId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getAssignedLastName() {
        return assignedLastName;
    }
    
    public void setAssignedLastName(String assignedLastName) {
        this.assignedLastName = assignedLastName;
    }
    
    public String getDateCompleted() {
        return dateCompleted;
    }
    
    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
}
