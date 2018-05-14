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
public class ViewProducts{
    final private  SimpleIntegerProperty productId;
   final private  SimpleStringProperty productName;
   final private  SimpleStringProperty productDescription;
   final private  SimpleStringProperty buyingPrice;
   final private  SimpleStringProperty sellingPrice;
 
    
   ViewProducts(int id,String pname,String description,String bprice,String sprice){
       this.productId = new SimpleIntegerProperty(id); 
       this.productName = new SimpleStringProperty(pname);
        this.productDescription = new SimpleStringProperty(description);
        this.buyingPrice = new SimpleStringProperty(bprice);
        this.sellingPrice = new SimpleStringProperty(sprice);
        
    }
   //properties
     public SimpleIntegerProperty getProductIdProperty(){
        return productId;
    }
   public SimpleStringProperty getProductNameProperty(){
        return productName;
    }
     public SimpleStringProperty getProductDescriptionProperty(){
        return productDescription;
    }
      public SimpleStringProperty getBuyingPriceProperty(){
        return buyingPrice;
    }
    public SimpleStringProperty getSellingPriceProperty(){
        return sellingPrice;
    }
    
   
   //Getters
     public int getProductId(){
        return this.productId.get();
    }
    public String getProductName(){
        return this.productName.get();
    }
      public String getProductDescription(){
        return this.productDescription.get();
    }
     public String getBuyingPrice(){
        return this.buyingPrice.get();
    }
      public String getSellingPrice(){
        return this.sellingPrice.get();
    }
     
   
   //setters
      public void setProductId(int id){
        this.productId.set(id);
    }
      public void setProductName(String pname){
        this.productName.set(pname);
    }
     public void setProductDescription(String description){
      this.productDescription.set(description);
    }
      public void setBuyingPrice(String bprice){
       this.buyingPrice.set(bprice);
    }
   
    public void setSellingPrice(String sprice){
       this.sellingPrice.set(sprice);
    }
    
   
}
