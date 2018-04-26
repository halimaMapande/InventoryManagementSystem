/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;




public class ViewCustomers  {
    
    private  String fName;
    private  String lName;
    private  String email;
    private  String phone;
    
     ViewCustomers(){
   this.fName="";
   this.lName="";
   this.email="";
   this.phone="";
     }
   ViewCustomers(String fname,String lname,String em,String phone){
        this.fName=fname;
        this.lName=lname;
        this.email=em;
        this.phone=phone;
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
    
     public String getEmail(){
        return email;
    }
    public void setEmail(String em){
       this.email=em;
    }
     public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
       this.phone=phone;
    }
    
}
