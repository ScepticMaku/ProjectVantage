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
        String query = "SELECT user.id, first_name, middle_name, last_name, email, phone_number, username, salt, password, secret_key, role.name AS role, status.name AS status "
                + "FROM user INNER JOIN role ON user.role_id = role.id INNER JOIN status ON user.status_id = status.id WHERE username = '" + username + "'";
        
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
        String query = "SELECT user.id, first_name, middle_name, last_name, email, phone_number, username, salt, password, secret_key, role.name AS role, status.name AS status "
                + "FROM user INNER JOIN role ON user.role_id = role.id INNER JOIN status ON user.status_id = status.id WHERE email = '" + email + "'";
        
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
    
//    public String getUsername(String user) {
//        try(ResultSet result = db.getData("SELECT username FROM user  WHERE username = '" + user + "'")) {
//            if(result.next())
//                return result.getString("username");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Database error: " + e.getMessage());
//        }
//        return null;
//    }
//    
//    public String getEmail(String user) {
//        try(ResultSet result = db.getData("SELECT email FROM user  WHERE username = '" + user + "'")) {
//            if(result.next())
//                return result.getString("email");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Database error: " + e.getMessage());
//        }
//        return null;
//    }
//    
//    public String getPassword(String user) {
//        try(ResultSet result = db.getData("SELECT password FROM user  WHERE username = '" + user + "'")) {
//            if(result.next())
//                return result.getString("password");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Database error: " + e.getMessage());
//        }
//        return null;
//    }
//    
//    public String getSalt(String user) {
//        try(ResultSet result = db.getData("SELECT salt FROM user WHERE username = '" + user + "'")) {
//            if(result.next())
//                return result.getString("salt");
//        } catch (SQLException e) {
//            System.out.println("Database error: " + e.getMessage());
//        }
//        return null;
//    }
//    
//    public String getRole(String user) {
//        try(ResultSet result = db.getData("SELECT role.name FROM user INNER JOIN role ON user.role_id = role.id WHERE username = '" + user + "'")) {
//            if(result.next())
//                return result.getString("role.name");
//        } catch (SQLException e) {
//            System.out.println("Database error: " + e.getMessage());
//        }
//        return null;
//    }
//    
//    public String getStatus(String user) {
//        try(ResultSet result = db.getData("SELECT status.name FROM user INNER JOIN status ON user.status_id = status.id WHERE username = '" + user + "'")) {
//            if(result.next())
//                return result.getString("status.name");
//        } catch (SQLException e) {
//            System.out.println("Database error: " + e.getMessage());
//        }
//        return null;
//    }
//    
//    public int getUserId(String user) {
//        try(ResultSet result = db.getData("SELECT id FROM user WHERE username = '" + user + "'")) {
//            if(result.next())
//                return result.getInt("id");
//        } catch (SQLException e) {
//            System.out.println("Database error: " + e.getMessage());
//        }
//        return 0;
//    }
//    
//    public String getSessionToken(int userId) {
//        try(ResultSet result = db.getData("SELECT session_token FROM session WHERE user_id = '" + userId + "'")) {
//            if(result.next())
//                return result.getString("session_token");
//        } catch (SQLException e) {
//            System.out.println("Database error: " + e.getMessage());
//        }
//        return null;
//    }
}

