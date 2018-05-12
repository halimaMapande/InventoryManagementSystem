/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author evod
 */
public class AdminPage {
    int userId;
    Stock stock;
    Suppliers suppliers;
    Products products;
    Customers customers;
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
    
     
    Scene scene;

    public Scene getScene() {
        BorderPane borderPane = new BorderPane();
        tabs = new TabsClass(userId);
        //TabPane user = tabs.usersTab();
         users=new Users();
         suppliers=new Suppliers();
         products=new Products();
         customers=new Customers();
         stock=new Stock();

        scene = new Scene(borderPane, 1400, 700);
        StackPane hbox = new StackPane();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setStyle("-fx-background-color:rgb(153,153,153);");
        ImageView imv = new ImageView();
        File file = new File("Untitled-1.jpg");
        Image img = new Image(file.toURI().toString(), 800, 50, true, true);
        imv.setImage(img);

        hbox.getChildren().add(imv);

        borderPane.setTop(hbox);

        VBox leftMenu = new VBox(8);
        leftMenu.setStyle("-fx-background-color:rgb(0,204,204);");
        leftMenu.prefWidthProperty().bind(scene.widthProperty().divide(4));
        leftMenu.setPadding(new Insets(20, 5, 5, 35));
        borderPane.setLeft(leftMenu);

        Label productLbl = new Label("Register Products");
        productLbl.setTextAlignment(TextAlignment.CENTER);
        productLbl.prefWidthProperty().bind(leftMenu.prefWidthProperty());
        productLbl.setPadding(new Insets(4, 4, 4, 4));

        productLbl.setOnMouseClicked(e -> {
            products=new Products();
            borderPane.setCenter(products.productTab());
        });

        Label supplierLbl = new Label("Register Suppliers");
        supplierLbl.setOnMouseClicked(e -> {
            suppliers=new Suppliers();
            borderPane.setCenter(suppliers.suppliersTab());
        });

        Label salesLbl = new Label("Sales");
        salesLbl.setOnMouseClicked(e->{
           borderPane.setCenter(tabs.salesTab());
        });
        Label stockLbl = new Label("Manage Stock");
        stockLbl.setOnMouseClicked(e->{
            stock=new Stock();
            borderPane.setCenter(stock.stockTab());
        });
        
        Label userLbl = new Label("Create Users");
        userLbl.setOnMouseClicked(e -> {
            users=new Users();
            borderPane.setCenter(users.usersTab());
        });

        Label customerLbl=new Label("Register Customers");
        customerLbl.setOnMouseClicked(e->{
            customers=new Customers();
          borderPane.setCenter(customers.customersTab());
        });
        
        Button logoutButton = new Button("Logout");
        logoutButton.setPadding(new Insets(700,4,4,4));
        logoutButton.setMaxWidth(100);
        logoutButton.setOnAction(e->{
             WholesaleInventorySystem.borderpane.setCenter(WholesaleInventorySystem.stackPane);
            
        });
        leftMenu.getChildren().addAll(productLbl, supplierLbl, salesLbl, stockLbl, userLbl,customerLbl,logoutButton);

       

        scene.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm());

        return scene;
    }

//    public static void main(String[] args){
//       launch(args);
//   }    
}
