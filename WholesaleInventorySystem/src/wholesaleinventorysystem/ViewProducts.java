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
   final private  SimpleStringProperty productName;
   final private  SimpleStringProperty productDescription;
   final private  SimpleIntegerProperty buyingPrice;
   final private  SimpleIntegerProperty sellingPrice;
    
    
   ViewProducts(String pname,String description,int bprice,int sprice){
        this.productName = new SimpleStringProperty(pname);
        this.productDescription = new SimpleStringProperty(description);
        this.buyingPrice = new SimpleIntegerProperty(bprice);
        this.sellingPrice = new SimpleIntegerProperty(sprice);
    }
   //properties
    public SimpleStringProperty getProductNameProperty(){
        return productName;
    }
     public SimpleStringProperty getProductDescriptionProperty(){
        return productDescription;
    }
      public SimpleIntegerProperty getBuyingPriceProperty(){
        return buyingPrice;
    }
    public SimpleIntegerProperty getSellingPriceProperty(){
        return sellingPrice;
    }
   
   //Getters
     public String getProductName(){
        return this.productName.get();
    }
      public String getProductDescription(){
        return this.productDescription.get();
    }
     public int getBuyingPrice(){
        return this.buyingPrice.get();
    }
      public int getSellingPrice(){
        return this.sellingPrice.get();
    }
   
   //setters
      public void setProductName(String pname){
        this.productName.set(pname);
    }
     public void setProductDescription(String description){
      this.productDescription.set(description);
    }
      public void setBuyingPrice(int bprice){
       this.buyingPrice.set(bprice);
    }
   
    public void setSellingPrice(int sprice){
       this.sellingPrice.set(sprice);
    }
   
}
