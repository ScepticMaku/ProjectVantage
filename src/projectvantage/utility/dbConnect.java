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
    
    // XAMPP 
    private static final String URL = "jdbc:mysql://localhost:3306/projectvantage_db";
    private static final String USER = "root";
    private static final String PASS = "";
    
    // RAILWAY
//    private static final String URL = "jdbc:mysql://root:nDxxRJUQVuJHamVtSNNsSFYekGyWkqpm@crossover.proxy.rlwy.net:22322/railway";
//    private static final String USER = "root";
//    private static final String PASS = "nDxxRJUQVuJHamVtSNNsSFYekGyWkqpm";
    
    private Connection connect;
    
    public dbConnect(){
        try {
            connect = DriverManager.getConnection(URL, USER, PASS);
        } catch(SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
    
    public ResultSet getData(String sql) throws SQLException {
        Statement state = connect.createStatement();
        ResultSet result = state.executeQuery(sql);
        return result;
    }
    
     
    public boolean executeQuery(String query, Object... values) {
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]); // Set values dynamically
            }
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Returns true if the execution was successful
        } catch (SQLException e) {
            System.out.println("Execution failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
