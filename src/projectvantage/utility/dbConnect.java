/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.sql.*;

/**
 *
 * @author Mark
 */
public class dbConnect {
    
    private Connection connect;
    
    public dbConnect(){
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectvantage_db", "root", "");
        } catch(SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
    
    public ResultSet getData(String sql) throws SQLException {
        Statement state = connect.createStatement();
        ResultSet result = state.executeQuery(sql);
        return result;
    }
    
     
    public boolean insertData(String query, Object... values) {
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]); // Set values dynamically
            }
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Returns true if the insertion was successful
        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateData(String query, Object... values) {
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]); // Set values dynamically
            }
            return pstmt.executeUpdate() > 0; // Returns true if the update was successful
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
