/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.models;

import java.time.LocalDate;

/**
 *
 * @author Mark Work Account
 */
public class Project {
    private int id;
    private String name;
    private String description;
    private LocalDate creation_date;
    private LocalDate due_date;
    private String creatorName;
    private String status;
    
    public Project(int id, String name, String description, LocalDate creation_date, LocalDate due_date, String creatorName, String status){
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
    
    public LocalDate getCreationDate() {
        return creation_date;
    }
    
    public LocalDate getDueDate() {
        return due_date;
    }
    
    public String getCreatorName() {
        return creatorName;
    }
    
    public String getStatus() {
        return status;
    }
}