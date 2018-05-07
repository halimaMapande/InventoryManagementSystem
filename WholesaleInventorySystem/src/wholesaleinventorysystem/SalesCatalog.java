/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class SalesCatalog {
   final private  SimpleStringProperty productName;
   final private  SimpleIntegerProperty quantity;
   final private SimpleIntegerProperty sales;

    /**
     *
     */
    public static int totalsales;
   
   SalesCatalog(String pname,int quan,int sale){
   this.productName = new SimpleStringProperty(pname);
   this.quantity = new SimpleIntegerProperty(quan);
   this.sales=new SimpleIntegerProperty(sale);
    }
   
    //properties
    public SimpleStringProperty getProductNameProperty(){
        return productName;
    }
     public SimpleIntegerProperty getQuantityProperty(){
        return quantity;
    }
     public SimpleIntegerProperty getSalesProperty(){
         return sales;
     }
     
     //Getters
      public String getProductName(){
        return this.productName.get();
    }
     public int getQuantity(){
        return this.quantity.get();
    }
     public int getSales(){
         return this.sales.get();
     }
     
     //setters
      public void setProductName(String pname){
        this.productName.set(pname);
    }
     public void setquantity(int quan){
        this.quantity.set(quan);
    }
     public void setSales(int sale){
    this.sales.set(sale);
}
}