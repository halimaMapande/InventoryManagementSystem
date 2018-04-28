/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
//import static javafx.application.Application.launch;


public class DbConnect {
   
    
    public static Connection getConnection(){
         Connection conn=null;
        try{
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement","root","");
            
          // Class.forName("org.sqlite.JDBC");
          // conn=DriverManager.getConnection("jdbc:sqlite:E:\\InventoryManagementSystem\\InventoryManagement.db");
          // System.out.println("Connection established");
          return conn;
        }
        
        catch(SQLException e){
            System.out.println(e);
            
        }
        return null;
    }
    
            
}
