/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

/**
 *
 * @author markj
 */
public class SessionConfig {
    private static SessionConfig instance;
    
    private int id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String username;
    private String role;
    
    public SessionConfig() {
        instance = this;
    }
    
    public static SessionConfig getInstance() {
        return instance;
    }
    
    public void setSession(int id, String first_name, String middle_name, String last_name, String email, String phone_number, String username, String role) {
        this.id = id;   
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.username = username;
        this.role = role;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }
    
    public String getFirstName() {
        return first_name;
    }
    
    public void setMiddleName(String middle_name) {
        this.middle_name = middle_name;
    }
    
    public String getMiddleName() {
        return middle_name;
    }
    
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
    
    public String getLastName() {
        return last_name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }
    
    public String getPhoneNumber() {
        return phone_number;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }
}
