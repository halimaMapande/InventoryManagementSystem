/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;




public class ViewCustomers  {
    private final SimpleIntegerProperty customerId;
    private final SimpleStringProperty fName;
    private final SimpleStringProperty lName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phoneNumber;
    
    
   ViewCustomers(int id,String fname,String lname,String em,String phone){
    this.customerId=new SimpleIntegerProperty(id);    
    this.fName=new SimpleStringProperty(fname);
    this.lName=new SimpleStringProperty(lname);
    this.email=new SimpleStringProperty(em);
    this.phoneNumber=new SimpleStringProperty(phone);
    }
   
   
   public SimpleIntegerProperty getCustomerIdProperty(){
       return customerId;
   }
    public SimpleStringProperty getFNameProperty(){
        return fName;
    }
   
    public SimpleStringProperty getLNameProperty(){
        return lName;
    }
     public SimpleStringProperty getEmailProperty(){
        return email;
    }
   
     public SimpleStringProperty getPhoneNumberProperty(){
        return phoneNumber;
    }
    
    
    
     //getters
     public int getCustomerId(){
         return this.customerId.get();
     }
      public String getFName(){
        return this.fName.get();
    }
      public String getLName(){
        return this.lName.get();
    }
       public String getEmail(){
        return this.email.get();
    }
      public String getPhoneNumber(){
        return this.phoneNumber.get();
    }
    
    //setters
      public void setCustomerId(int id){
          this.customerId.set(id);
      }
      public void setFName(String fname){
        this.fName.set(fname);
    }
     public void setLName(String lname){
      this.lName.set(lname);
    }
      public void setEmail(String em){
        this.email.set(em);
    }
      public void setPhoneNumber(String phone){
        this.phoneNumber.set(phone);
    }
   
   
   
   
   
   
   
    
    
}
