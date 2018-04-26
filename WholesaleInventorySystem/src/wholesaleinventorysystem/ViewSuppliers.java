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
public class ViewSuppliers{
    private int supplierId;
    private  String SupplierName;
    private  String phoneNumber;
    private  String email;
    private  String address;
   
    
     ViewSuppliers(){
   this.supplierId=0;
   this.SupplierName="";
   this.phoneNumber="";
   this.email="";
   this.address="";
     }
  
     ViewSuppliers(int supplierId,String SupplierName,String phoneNumber,String email,String address){
         this.supplierId=supplierId;
        this.SupplierName=SupplierName;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.address=address;
    }
     
      public int getId(){
        return supplierId;
    }
    public void setId(int supplierId){
       this.supplierId=supplierId;
    }
   
     public String getName(){
        return SupplierName;
    }
    public void setName(String SupplierName){
       this.SupplierName=SupplierName;
    }
     public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
      this.phoneNumber=phoneNumber;
    }
    
     public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
     public String getAddress(){
        return address;
    }
    public void setAddress(String address){
       this.address=address;
    }
    
}
