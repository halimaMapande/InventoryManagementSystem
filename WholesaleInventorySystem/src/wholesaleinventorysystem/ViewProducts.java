/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;


/**
 *
 * @author evod
 */
public class ViewProducts{
    private  String productName;
    private  String productDescription;
    private  int buyingPrice;
    private  int sellingPrice;
    
     ViewProducts(){
   this.productName="";
   this.productDescription="";
   this.buyingPrice=0;
   this.sellingPrice=0;
     }
   ViewProducts(String pname,String description,int bprice,int sprice){
        this.productName=pname;
        this.productDescription=description;
        this.buyingPrice=bprice;
        this.sellingPrice=sprice;
    }
    public String getName(){
        return productName;
    }
    public void setName(String pname){
       this.productName=pname;
    }
     public String getDescription(){
        return productDescription;
    }
    public void setDescription(String description){
      this.productDescription=description;
    }
   
     public int getBPrice(){
        return buyingPrice;
    }
    public void setBPrice(int bprice){
       this.buyingPrice=bprice;
    }
     public int getSPrice(){
        return sellingPrice;
    }
    public void setSPrice(int sprice){
       this.sellingPrice=sprice;
    }
}
