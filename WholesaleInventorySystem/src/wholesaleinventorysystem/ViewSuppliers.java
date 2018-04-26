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
    private int id;
    private  String supplierName;
    private  String phoneNumber;
    private  String email;
    private  String address;
   
    
     ViewSuppliers(){
   this.id=0;
   this.supplierName="";
   this.phoneNumber="";
   this.email="";
   this.address="";
     }
  
     ViewSuppliers(int id,String name,String phone,String em,String add){
         this.id=id;
        this.supplierName=name;
        this.phoneNumber=phone;
        this.email=em;
        this.address=add;
    }
     
      public int getId(){
        return id;
    }
    public void setId(int id){
       this.id=id;
    }
   
     public String getName(){
        return supplierName;
    }
    public void setName(String name){
       this.supplierName=name;
    }
     public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phone){
      this.phoneNumber=phone;
    }
    
     public String getEmail(){
        return email;
    }
    public void setEmail(String em){
        this.email=em;
    }
     public String getAddress(){
        return address;
    }
    public void setAddress(String add){
       this.address=add;
    }
    
}
