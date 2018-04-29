/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author evod
 */
public class ViewSuppliers{
    private  StringProperty supplierName;
    private  StringProperty phoneNumber;
    private  StringProperty email;
    private  StringProperty address;

    public ViewSuppliers(String supplierName, String phoneNumber, String email, String address) {
        this.supplierName = new SimpleStringProperty(supplierName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
    }

    public StringProperty getSupplierNameProperty() {
        return supplierName;
    }

    public StringProperty getPhoneNumberProperty() {
        return phoneNumber;
    }

    public StringProperty getEmailProperty() {
        return email;
    }

    public StringProperty getAddressProperty() {
        return address;
    }
  
    //Getters
    public String getSupplierName(){
        return this.supplierName.get();
    }
    
    public String getPhoneNumber(){
        return this.phoneNumber.get();
    }
    
    public String getEmail(){
        return this.email.get();
    }
    
    public String getAddress(){
        return this.address.get();
    }
    
    //Setters
    public void setSupplierName(String supplierName){
        this.supplierName.set(supplierName);
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber.set(phoneNumber);
    }
    public void setEmail(String email){
        this.email.set(email);
    }
    public void setAddress(String address){
        this.address.set(address);
    }
}
