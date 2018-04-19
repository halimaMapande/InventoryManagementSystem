/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;


import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author evod
 */
public class AdminPage{
    Label fNameLabel,lNameLabel,phoneLabel,emailLabel,roleLabel,userLabel,passLabel,topLabel;
    TextField fNameField,lNameField,phoneField,emailField,roleField,userField;
    PasswordField passField;
    Button saveButton;
    Connection conn=null;
    PreparedStatement statement=null;
    TabsClass tabs;
    Scene scene;
    
    public Scene getScene(){
        BorderPane borderPane = new BorderPane();
        tabs = new TabsClass();
        TabPane user = tabs.usersTab();
        
        scene = new Scene(borderPane, 900, 500);
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
         ImageView imv = new ImageView();
        //Image img = new Image(AdminPage.class.getResourceAsStream("button.png"));
        //imv.setImage(img);
        
        hbox.getChildren().add(imv);
        
        //BorderPane.setAlignment(topLabel,Pos.TOP_CENTER);
        borderPane.setTop(hbox);

        VBox leftMenu = new VBox(8);
        leftMenu.setStyle("-fx-background-color:cyan");
        leftMenu.prefWidthProperty().bind(scene.widthProperty().divide(4));
        leftMenu.setPadding(new Insets(20, 5, 5, 25));
        borderPane.setLeft(leftMenu);

        //Text text = new Text("Product");
        //text.setFont(new Font("sans-serif", 18.0));
        //StackPane stack = new StackPane(text);
        Label productLbl = new Label("Product");
        productLbl.setTextAlignment(TextAlignment.CENTER);
        // stack.setStyle("-fx-background-color:blue;");
        productLbl.prefWidthProperty().bind(leftMenu.prefWidthProperty());
        productLbl.setPadding(new Insets(4, 4, 4, 4));

        productLbl.setOnMouseClicked(e -> {
            borderPane.setCenter(tabs.productTab());
        });
        
        
        Label supplierLbl = new Label("Supplier");
        supplierLbl.setOnMouseClicked(e -> {
            borderPane.setCenter(tabs.suppliersTab());
        });
        supplierLbl.setTextAlignment(TextAlignment.CENTER);
        Label salesLbl = new Label("Sales");
        Label stockLbl = new Label("Stock");
        Label userLbl = new Label("Users");
        userLbl.setOnMouseClicked(e -> {
            borderPane.setCenter(tabs.usersTab());
        });

        leftMenu.getChildren().addAll(productLbl, supplierLbl, salesLbl, stockLbl, userLbl);

        Label logoutLbl = new Label("Logout");
        //logoutLbl.setPadding(new Insets(0,0,0,0));
        HBox hbox2 = new HBox();
        hbox2.setStyle("-fx-background-color:gray;");
        hbox2.setPadding(new Insets(10, 10, 10, 10));
        hbox2.setSpacing(10);
        hbox2.getChildren().add(logoutLbl);
        borderPane.setBottom(hbox2);
        
        scene.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm());
        
        return scene;
    }
    
        
//    public static void main(String[] args){
//       launch(args);
//   }    
}
