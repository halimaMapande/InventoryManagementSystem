/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import java.awt.Image;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**
 *
 * @author josephine
 */
public class EmployeePage {
    Scene scene;
    TabsClass tabs;
    int userId;

    public EmployeePage(int id) {
        this.userId = id;
    }
   
    public Scene getScene() {
     tabs=new TabsClass(userId);
     Customers customers=new Customers();
    BorderPane border=new BorderPane();
    scene = new Scene(border, 1400, 700);
        
    VBox leftMenu=new VBox(8);
    leftMenu.setStyle("-fx-background-color:cyan;");
    leftMenu.prefWidthProperty().bind(scene.widthProperty().divide(4));
    leftMenu.setPadding(new Insets(20, 5, 5, 10));        
    
    Label label1=new Label("Sales");
    label1.setOnMouseClicked(e -> {
    border.setCenter(tabs.salesTab());
    });
           
    Label label2=new Label("Customers");
    label2.setOnMouseClicked(e -> {
    border.setCenter(customers.customersTab());
    });
    leftMenu.getChildren().addAll(label1,label2);
    border.setLeft(leftMenu);
           
    Label label3=new Label("Logout");
    HBox bottomMenu=new HBox();
    bottomMenu.setStyle("-fx-background-color:gray;");
    bottomMenu.setPadding(new Insets(10, 10, 10, 10));
    bottomMenu.getChildren().add(label3);
    border.setBottom(bottomMenu);
           
    Label iconLbl=new Label("Icon stays here..!");
    
         
        
    StackPane stack=new StackPane();
    stack.setStyle("-fx-background-color:gray;");
    stack.getChildren().add(iconLbl);
    border.setTop(stack);
           
        
        
    scene.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm());
        
    
    
    return scene;
    }

}
