/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Sadick
 */
class Item {
    final private SimpleStringProperty  item1;
    final private SimpleStringProperty  item2;
    
    Item(String item1,String item2){
    this.item1=new SimpleStringProperty (item1);
    this.item2=new SimpleStringProperty (item2);
    }
     public SimpleStringProperty getItem1Property(){
        return item1;
    }

    public SimpleStringProperty getItem2roperty(){
        return item2;
    }
    public String getItem1(){
      return this.item1.get();
    }
    public String getItem2(){
      return this.item2.get();
    }
    public void setItem1(String item1){
        this.item1.set(item1);
    }
    public void setItem2(String item2){
        this.item2.set(item2);
    }
    
            
    
}
