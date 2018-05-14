/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import static wholesaleinventorysystem.WholesaleInventorySystem.borderpane;


public class AdminPage {
    int userId;
    Stock stock;
    Suppliers suppliers;
    Products products;
    Customers customers;
    ChangePassword change;
    Users users;
    public AdminPage(int id) {
        this.userId = id;
    }
    
    Label fNameLabel, lNameLabel, phoneLabel, emailLabel, roleLabel, userLabel, passLabel, topLabel;
    TextField fNameField, lNameField, phoneField, emailField, roleField, userField;
    PasswordField passField;
    Button saveButton;
    Connection conn = null;
    PreparedStatement statement = null;
    TabsClass tabs;
    DropShadow shadow1,shadow2,shadow3,shadow4,shadow5,shadow6,shadow7;
     
    Scene scene;

    public Scene getScene() {
        
        BorderPane borderPane = new BorderPane();
        tabs = new TabsClass(userId);
        //TabPane user = tabs.usersTab();
        change=new ChangePassword();
         users=new Users();
         suppliers=new Suppliers();
         products=new Products();
         customers=new Customers();
         stock=new Stock();

        scene = new Scene(borderPane, 1500, 750);
        StackPane hbox = new StackPane();
        hbox.setPadding(new Insets(3, 0, 3, 0));
        hbox.setStyle("-fx-background-color:rgb(153,153,153);");
        File file=new File("images/logo.png");
        Image img = new Image(file.toURI().toString(),1500,100,true,true);
        ImageView imv = new ImageView(img);
        

        hbox.getChildren().add(imv);

        borderPane.setTop(hbox);

        VBox leftMenu = new VBox(25);
        leftMenu.setStyle("-fx-background-color:rgb(0,204,204);");
        leftMenu.prefWidthProperty().bind(scene.widthProperty().divide(4));
        leftMenu.setPadding(new Insets(20, 5, 5, 35));
        borderPane.setLeft(leftMenu);
        
        

       Button productLbl = new Button("REGISTER PRODUCTS");
       productLbl.setMaxWidth(200);
        productLbl.setOnMouseEntered(e->{
            shadow1=new DropShadow();
            productLbl.setEffect(shadow1);
        });
        productLbl.setOnMouseExited(e->{
            productLbl.setEffect(null);
        });
        //productLbl.setTextAlignment(TextAlignment.CENTER);
        productLbl.prefWidthProperty().bind(leftMenu.prefWidthProperty());
        productLbl.setPadding(new Insets(4, 4, 4, 4));

        productLbl.setOnMouseClicked(e -> {
            products=new Products();
            borderPane.setCenter(products.productTab());
        });

        Button supplierLbl = new Button("REGISTER SUPPLIERS");
        supplierLbl.setMaxWidth(200);
        supplierLbl.setOnMouseEntered(e->{
            shadow2=new DropShadow();
            supplierLbl.setEffect(shadow2);
        });
        supplierLbl.setOnMouseExited(e->{
           supplierLbl.setEffect(null);
        });
        supplierLbl.setOnMouseClicked(e -> {
            suppliers=new Suppliers();
            borderPane.setCenter(suppliers.suppliersTab());
        });

        Button salesLbl = new Button("SALES");
        salesLbl.setMaxWidth(200);
        salesLbl.setOnMouseEntered(e->{
            shadow3=new DropShadow();
            salesLbl.setEffect(shadow3);
        });
        salesLbl.setOnMouseExited(e->{
            salesLbl.setEffect(null);
        });
        salesLbl.setOnMouseClicked(e->{
           borderPane.setCenter(tabs.salesTab());
        });
        Button stockLbl = new Button("MANAGE STOCK");
        stockLbl.setMaxWidth(200);
        stockLbl.setOnMouseEntered(e->{
            shadow4=new DropShadow();
            stockLbl.setEffect(shadow4);
        });
        stockLbl.setOnMouseExited(e->{
            stockLbl.setEffect(null);
        });
        stockLbl.setOnMouseClicked(e->{
            stock=new Stock();
            borderPane.setCenter(stock.stockTab());
        });
        //InputStream input=getClass().getResourceAsStream("images/user.png");
        //Image userIcon=new Image(input);

        Button userLbl = new Button("CREATE USERS");
        //userLbl.setGraphic(new ImageView(userIcon));
        userLbl.setMaxWidth(200);
        userLbl.setOnMouseEntered(e->{
            shadow5=new DropShadow();
            userLbl.setEffect(shadow5);
        });
        userLbl.setOnMouseExited(e->{
            userLbl.setEffect(null);
        });
        userLbl.setOnMouseClicked(e -> {
            users=new Users();
            borderPane.setCenter(users.usersTab());
        });

        Button customerLbl=new Button("REGISTER CUSTOMERS");
        customerLbl.setMaxWidth(200);
        customerLbl.setOnMouseEntered(e->{
            shadow6=new DropShadow();
            customerLbl.setEffect(shadow6);
        });
        customerLbl.setOnMouseExited(e->{
            customerLbl.setEffect(null);
        });
        customerLbl.setOnMouseClicked(e->{
            customers=new Customers();
          borderPane.setCenter(customers.customersTab());
        });
        
         Button passBtn=new Button("CHANGE PASSWORD");
        passBtn.setMaxWidth(200);
        passBtn.setOnMouseEntered(e->{
            shadow6=new DropShadow();
            passBtn.setEffect(shadow6);
        });
        passBtn.setOnMouseExited(e->{
            passBtn.setEffect(null);
        });
        passBtn.setOnMouseClicked(e->{
           borderPane.setCenter(change.passwordTab());
        });
        
        Button logoutButton = new Button("Logout");
      
        logoutButton.setMaxWidth(200);
        logoutButton.setOnMouseEntered(e->{
            shadow7=new DropShadow();
            logoutButton.setEffect(shadow7);
        });
        logoutButton.setOnMouseExited(e->{
            logoutButton.setEffect(null);
        });
        
        logoutButton.setOnAction(e->{
             WholesaleInventorySystem wis=new WholesaleInventorySystem();
            
            
        });
        HBox logoutPane=new HBox();
        logoutPane.prefWidthProperty().bind(scene.widthProperty().divide(4));
        logoutPane.setPadding(new Insets(150, 0, 0, 0));
        logoutPane.getChildren().add(logoutButton);
        leftMenu.getChildren().addAll(productLbl, supplierLbl, salesLbl, stockLbl, userLbl,customerLbl,passBtn,logoutPane);
        
        
       

        scene.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm());

        return scene;
    }

//    public static void main(String[] args){
//       launch(args);
//   }    
}
