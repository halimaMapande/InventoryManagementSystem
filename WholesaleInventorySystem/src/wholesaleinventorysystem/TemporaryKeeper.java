/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class TemporaryKeeper {
     PreparedStatement statement = null;
    ResultSet rs;
    Connection conn = null;
    
   final private  SimpleStringProperty productName;
   final private  SimpleIntegerProperty quantity;
   
   TemporaryKeeper(String pname,int quan){
       
       this.productName = new SimpleStringProperty(pname);
        this.quantity = new SimpleIntegerProperty(quan);
    }
   
    //properties
    public SimpleStringProperty getProductNameProperty(){
        return productName;
    }
     public SimpleIntegerProperty getQuantityProperty(){
        return quantity;
    }
     
     //Getters
      public String getProductName(){
        return this.productName.get();
    }
     public int getQuantity(){
        return this.quantity.get();
    }
     
     //setters
      public void setProductName(String pname){
        this.productName.set(pname);
    }
     public void setquantity(int quan){
        this.quantity.set(quan);
    }
     public int productId(String pro){
         String query1 = "select ProductId from product where ProductName=?";
               
         try {
              conn = DbConnect.getConnection();
             statement = conn.prepareStatement(query1);
             statement.setString(1,pro);
              rs = statement.executeQuery();
              while(rs.next()){
                    return rs.getInt("ProductId");
                }
                rs.close();
                statement.close();
                conn.close();
         } catch (SQLException ex) {
             Logger.getLogger(TemporaryKeeper.class.getName()).log(Level.SEVERE, null, ex);
         }
               
     
      return 0;
     }
      public boolean stockverify(String pro){
         String query1 = "select stock.productId from stock join product on "
                 + "product.ProductId=stock.productId where ProductName=?";
               
         try {
              conn = DbConnect.getConnection();
             statement = conn.prepareStatement(query1);
             statement.setString(1,pro);
              rs = statement.executeQuery();
              while(rs.next()){
                   rs.getInt("ProductId");
                    return true;
                }
                rs.close();
                statement.close();
                conn.close();
         } catch (SQLException ex) {
             Logger.getLogger(TemporaryKeeper.class.getName()).log(Level.SEVERE, null, ex);
         }
               
     
      return false;
     }
     
   
  
}

    
    

