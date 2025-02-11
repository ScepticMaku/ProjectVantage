/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.models;

/**
 *
 * @author Mark
 */
public class User {
    private int id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String username;
    private String password;
    private String role;
    private String status;
    
    public User(int id, String first_name, String middle_name, String last_name,
            String email, String phone_number, String username, String password, 
            String role, String status) {
        this.id = id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }
    
    public int getId() {
        return id;
    }
    
    public String getFirstName() {
        return first_name;
    }
    
    public String getMiddleName() {
        return middle_name;
    }
    
    public String getLastName() {
        return last_name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phone_number;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getStatus() {
        return status;
    }
}   
