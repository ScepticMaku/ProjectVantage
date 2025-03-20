/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mark Work Account
 */
public class DatabaseConfig {
    dbConnect db = new dbConnect();
    
    public String getUsername(String user) {
        try(ResultSet result = db.getData("SELECT username FROM user  WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    public String getEmail(String user) {
        try(ResultSet result = db.getData("SELECT email FROM user  WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("email");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    public String getPassword(String user) {
        try(ResultSet result = db.getData("SELECT password FROM user  WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    public String getSalt(String user) {
        dbConnect db = new dbConnect();
        try(ResultSet result = db.getData("SELECT salt FROM user WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("salt");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    public String getRole(String user) {
        try(ResultSet result = db.getData("SELECT role FROM user WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("role");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
    
    public String getStatus(String user) {
        try(ResultSet result = db.getData("SELECT status FROM user WHERE username = '" + user + "'")) {
            if(result.next())
                return result.getString("status");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }
}
