/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import projectvantage.models.Project;
import projectvantage.models.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Mark Work Account
 */
public class DatabaseConfig {
    dbConnect db = new dbConnect();
    
    public User getUserByUsername(String username) {
        String query = "SELECT user.id, first_name, middle_name, last_name, email, phone_number, username, salt, password, secret_key, role.name AS role, user_status.name AS status "
                + "FROM user INNER JOIN role ON user.role_id = role.id INNER JOIN user_status ON user.status_id = user_status.id WHERE username = '" + username + "'";
        
        try(ResultSet result = db.getData(query)) {
            if(result.next()) {
                return new User(
                        result.getInt("user.id"),
                        result.getString("first_name"),
                        result.getString("middle_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("phone_number"),
                        result.getString("username"),
                        result.getString("salt"),
                        result.getString("password"),
                        result.getString("secret_key"),
                        result.getString("role"),
                        result.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserByEmail(String email) {
        String query = "SELECT user.id, first_name, middle_name, last_name, email, phone_number, username, salt, password, secret_key, role.name AS role, user_status.name AS status "
                + "FROM user INNER JOIN role ON user.role_id = role.id INNER JOIN user_status ON user.status_id = user_status.id WHERE email = '" + email + "'";
        
        try(ResultSet result = db.getData(query)) {
            if(result.next()) {
                return new User(
                        result.getInt("user.id"),
                        result.getString("first_name"),
                        result.getString("middle_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("phone_number"),
                        result.getString("username"),
                        result.getString("salt"),
                        result.getString("password"),
                        result.getString("secret_key"),
                        result.getString("role"),
                        result.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public int getUserRoleIdByUsername(String username) {
        String query = "SELECT role_id FROM user WHERE username = '" + username + "'";
        
        try(ResultSet result = db.getData(query)) {
            if(result.next()) {
                return result.getInt("role_id");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    public String getImagePath(String username) {
        String query = "SELECT user.username AS username, image_path FROM user_image INNER JOIN user ON user_image.user_id = user.id WHERE username = '" + username + "'";
        
        try(ResultSet result = db.getData(query)) {
            if(result.next()) {
                return result.getString("image_path");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private String formatDate(Date date) {
        return date.toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
    
    public Project getProjectById(int id) {
        String sql = "SELECT project.id, project.name, description, creation_date, due_date, user.last_name AS creator_name, project_status.name AS status "
                + "FROM project INNER JOIN project_status ON project.status_id = project_status.id INNER JOIN user ON project.user_id = user.id WHERE project.id = " + id;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return new Project(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        formatDate(result.getDate("creation_date")),
                        formatDate(result.getDate("due_date")),
                        result.getString("creator_name"),
                        result.getString("status")
               ); 
            } 
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public int getStatusIdById(int id) {
        String sql = "SELECT status_id FROM project WHERE id = " + id;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("status_id");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}

