/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author evod
 */
public class ViewUsers{
    private final SimpleStringProperty fName;
    private final SimpleStringProperty lName;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    private final SimpleStringProperty role;
   
    
    
  
     ViewUsers(String fname,String lname,String phone,String em,String user,String pass,String rol){
         
        this.fName=new SimpleStringProperty (fname);
        this.lName=new SimpleStringProperty (lname);
        this.phoneNumber=new SimpleStringProperty (phone);
        this.email=new SimpleStringProperty (em);
        this.username= new SimpleStringProperty (user);
        this.password=new SimpleStringProperty (pass);
        this.role=new SimpleStringProperty (rol);
        
    }
  
   
     public String getfName(){
        return fName.get();
    }
    public void setfName(String fname){
        fName.set(fname);
    }
    
    public String getlName(){
        return lName.get();
    }
    public void setlName(String lname){
       lName.set(lname);
    }
     public String getPhoneNumber(){
        return phoneNumber.get();
    }
    public void setPhoneNumber(String phone){
      phoneNumber.set(phone);
    }
    
     public String getEmail(){
        return email.get();
    }
    public void setEmail(String em){
        email.set(em);
    }
     public String getUsername(){
        return username.get();
    }
    public void setUsername(String user){
        username.set(user);
    }
    
     public String getPassword(){
        return password.get();
    }
    public void setPassword(String pass){
        password.set(pass);
    }
    
     public String getRole(){
        return role.get();
    }
    public void setRole(String rol){
        role.set(rol);
    }
    
    
}
