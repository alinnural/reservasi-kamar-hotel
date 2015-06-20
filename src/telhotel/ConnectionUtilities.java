/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package telhotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alin
 */
class ConnectionUtilities {
    private static Connection con;
    
    public static Connection getConnection(){
        if(con==null)
        {
            //error handling
            try{
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
            }catch(SQLException ex)
            {
                ex.toString();
            }
        }
        return con;
    
    }
    
}
