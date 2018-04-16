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
    private  String supplierName;
    private  String phoneNumber;
    private  String email;
    private  String address;
   
    
     ViewSuppliers(){
   this.supplierName="";
   this.phoneNumber="";
   this.email="";
   this.address="";
     }
  
     ViewSuppliers(String name,String phone,String em,String add){
        this.supplierName=name;
        this.phoneNumber=phone;
        this.email=em;
        this.address=add;
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
