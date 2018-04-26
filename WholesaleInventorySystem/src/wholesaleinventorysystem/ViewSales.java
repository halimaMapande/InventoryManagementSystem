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
public class ViewSales{
    private  String fName;
    private  String lName;
    private  String productName;
    private  String description;
    private  int price;
    private  int quantity;
    private String time;
    
     ViewSales(){
        this.fName="";
        this.lName="";
        this.description="";
        this.price=0;
        this.quantity=0;
        this.time="";
     }
   ViewSales(String fname,String lname,String productName,String descr,int price,int quan,String time){
        this.fName=fname;
        this.lName=lname;
        this.description=descr;
        this.price=price;
        this.quantity=quan;
        this.time=time;
    }
   
    public String getFName(){
        return fName;
    }
    public void setFName(String fname){
       this.fName=fname;
    }
     public String getLName(){
        return lName;
    }
    public void setLName(String lname){
      this.lName=lname;
    }
    
    public String getName(){
        return productName;
    }
    public void setName(String pname){
       this.productName=pname;
    }
     public String getDescription(){
        return description;
    }
    public void setDescription(String descr){
      this.description=descr;
    }
   
     public int getPrice(){
        return price;
    }
    public void setBPrice(int price){
       this.price=price;
    }
     public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quan){
       this.quantity=quan;
    }
    
    public String getTime(){
        return time;
    }
    public void setTime(String time){
      this.time=time;
    }
}
