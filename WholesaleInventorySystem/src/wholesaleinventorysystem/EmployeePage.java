/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;


import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    
    TabsClass tabs;
    OperatorSales opSales;
    BorderPane border;
    int userId;

    public EmployeePage(int id) {
        this.userId = id;
    }
   
    public Scene getScene() {
      tabs=new TabsClass(userId);
    ChangePassword cp=new ChangePassword();
     Customers customers=new Customers();
     

    border=new BorderPane();
    Scene scene = new Scene(border, 1500, 750);
    
    StackPane stack = new StackPane();
       stack.setPadding(new Insets(3, 0, 3, 0));
        stack.setStyle("-fx-background-color:rgb(153,153,153);");
        File file=new File("images/logo.png");
       Image img = new Image(file.toURI().toString(),1500,100,true,true);
      
        ImageView imv = new ImageView(img);
        

        stack.getChildren().add(imv);
        border.setTop(stack);

        
    VBox leftMenu=new VBox(25);
    leftMenu.setStyle("-fx-background-color:rgb(0,204,204);");
    leftMenu.prefWidthProperty().bind(scene.widthProperty().divide(4));
    leftMenu.setPadding(new Insets(20, 5, 5, 35));        
    
    Button salesBtn=new Button("SALES");
    salesBtn.setMaxWidth(200);
    salesBtn.setOnMouseClicked(e -> {

    border.setCenter(tabs.salesTab());

    });
           
    Button customerBtn=new Button("CUSTOMER");
    customerBtn.setMaxWidth(200);
    customerBtn.setOnMouseClicked(e -> {

    border.setCenter(customers.customersTab());

    });
    
    Button passwordBtn=new Button("CHANGE PASSWORD");
    passwordBtn.setMaxWidth(200);
    passwordBtn.setOnMouseClicked(e -> {

    border.setCenter(cp.passwordTab());

    });
    
           
    Button logoutBtn=new Button("Logout");
    HBox bottomMenu=new HBox();
    bottomMenu.setPadding(new Insets(370, 0, 0, 0));
    bottomMenu.getChildren().add(logoutBtn);
   
    leftMenu.getChildren().addAll(salesBtn,customerBtn,passwordBtn,bottomMenu);
    border.setLeft(leftMenu);
    
    scene.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm());
        
    
    
    return scene;
    }

}
