/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import projectvantage.models.User;

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
            e.printStackTrace();
            System.out.println("Database Error: " + e.getMessage());
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
            e.printStackTrace();
            System.out.println("Database Error: " + e.getMessage());
        }
        return null;
    }
}

