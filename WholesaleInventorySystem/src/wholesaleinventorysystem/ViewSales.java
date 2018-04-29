/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author evod
 */
public class ViewSales{
   final private  SimpleStringProperty fName;
   final private  SimpleStringProperty  lName;
   final private  SimpleStringProperty productName;
   final private  SimpleStringProperty  description;
   final private  SimpleIntegerProperty price;
   final private  SimpleIntegerProperty quantity;
   final private SimpleStringProperty  time;
    
   
   ViewSales(String fname,String lname,String productName,String descr,int price,int quan,String time){
        
        this.fName = new SimpleStringProperty(fname);
        this.lName = new SimpleStringProperty(lname);
        this.productName = new SimpleStringProperty(productName);
        this.description = new SimpleStringProperty(descr);
        this.price = new SimpleIntegerProperty(price);
        this.quantity = new SimpleIntegerProperty(quan);
        this.time = new SimpleStringProperty(time);
    }

    
   
    public SimpleStringProperty getFNameProperty(){
        return fName;
    }

    public SimpleStringProperty getLNameProperty(){
        return lName;
    }
    
    public SimpleStringProperty getProductNameProperty(){
        return productName;
    }
    public SimpleStringProperty getDescriptionProperty(){
        return description;
    }
    public SimpleIntegerProperty getPriceProperty(){
        return price;
    }
    public SimpleIntegerProperty getQuantityProperty(){
        return quantity;
    }
    public SimpleStringProperty getTimeProperty(){
        return time;
    }
    
     //Getters
    public String getFName(){
        return this.fName.get();
    }
    
    public String getLName(){
        return this.lName.get();
    }
    
    public String getProductName(){
        return this.productName.get();
    }
    
    public String getDescription(){
        return this.description.get();
    }
    public int getPrice(){
        return this.price.get();
    }
    public int getQuantity(){
        return this.quantity.get();
    }
    public String getTime(){
        return this.time.get();
    }
    
    
    //Setters
    public void setFName(String fname){
        this.fName.set(fname);
    }
     public void setLName(String lname){
      this.lName.set(lname);
    }
     public void setProductName(String pname){
       this.productName.set(pname);
    }
    
     public void setDescription(String descr){
      this.description.set(descr);
    }
     public void setPrice(int price){
       this.price.set(price);
    }
    
   
    public void setQuantity(int quan){
       this.quantity.set(quan);
    }
    
    public void setTime(String time){
      this.time.set(time);
    }
}
