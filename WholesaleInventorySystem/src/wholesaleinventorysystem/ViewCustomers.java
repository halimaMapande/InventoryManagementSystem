/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;




public class ViewCustomers  {
    
    private  String productName;
    private  String productDescription;
    private  int quantity;
    private  int price;
    
     ViewCustomers(){
   this.productName="";
   this.productDescription="";
   this.quantity=0;
   this.price=0;
     }
   ViewCustomers(String pname,String description,int quan,int price){
        this.productName=pname;
        this.productDescription=description;
        this.quantity=quan;
        this.price=price;
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
    
     public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quan){
       this.quantity=quan;
    }
     public int getPrice(){
        return price;
    }
    public void setPrice(int price){
       this.price=price;
    }
    
}
