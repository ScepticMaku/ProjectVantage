/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.models;

import java.sql.Date;

/**
 *
 * @author Mark Work Account
 */
public class Project {
    private int id;
    private String name;
    private String description;
    private String creation_date;
    private String due_date;
    private String creatorName;
    private String status;
    
    public Project(int id, String name, String description, String creation_date, String due_date, String creatorName, String status){
        this.id = id;
        this.name = name;
        this.description = description;
        this.creation_date = creation_date;
        this.due_date = due_date;
        this.creatorName = creatorName;
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
        return creation_date;
    }
    
    public String getDueDate() {
        return due_date;
    }
    
    public String getCreatorName() {
        return creatorName;
    }
    
    public String getStatus() {
        return status;
    }
}