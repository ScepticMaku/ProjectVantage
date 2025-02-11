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
}
