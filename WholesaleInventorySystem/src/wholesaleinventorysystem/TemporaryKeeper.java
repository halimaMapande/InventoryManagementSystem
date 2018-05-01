/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class TemporaryKeeper {
    
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
     
   
  
}

    
    

