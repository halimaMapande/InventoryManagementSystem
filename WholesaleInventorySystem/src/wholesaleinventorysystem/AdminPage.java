/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author evod
 */
public class AdminPage extends Application {
    Label fNameLabel,lNameLabel,phoneLabel,emailLabel,roleLabel,userLabel,passLabel,topLabel;
    TextField fNameField,lNameField,phoneField,emailField,roleField,userField;
    PasswordField passField;
    Button saveButton;
    Scene scene;
    Connection conn=null;
    PreparedStatement statement=null;
    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();        
               
        primaryStage.setTitle("Hello World!");
        TabsClass tabs=new TabsClass();
       
        HBox hbox=new HBox();
        hbox.setPadding(new Insets(10,10,10,10));
        Button button1=new Button("File");
        Button button2=new Button("Edit");
        hbox.setStyle("-fx-background-color:gray");
        hbox.getChildren().addAll(button1,button2);
       //BorderPane.setAlignment(topLabel,Pos.TOP_CENTER);
        borderPane.setTop(hbox);
       
        VBox leftMenu = new VBox(8);
        leftMenu.setStyle("-fx-background-color:cyan");
        //leftMenu.prefWidthProperty().bind(scene.widthProperty().divide(4));
        leftMenu.setPadding(new Insets(20,5,5,5));
        borderPane.setLeft(leftMenu);
               
        Text text = new Text("Product");
        text.setFont(new Font("sans-serif",18.0));
        StackPane stack = new StackPane(text);
        Label productLbl = new Label("Product"); 
        productLbl.setTextAlignment(TextAlignment.CENTER);
       // stack.setStyle("-fx-background-color:blue;");
        stack.prefWidthProperty().bind(leftMenu.prefWidthProperty());
        stack.setPadding(new Insets(4,4,4,4));
        
        
        stack.setOnMouseClicked(e -> {
        borderPane.setCenter(tabs.productTab());
        });
       
        Label supplierLbl = new Label("Supplier");
       supplierLbl.setTextAlignment(TextAlignment.CENTER);
        Label customerLbl = new Label("Customer");
        Label salesLbl = new Label("Sales");
        Label stockLbl = new Label("Stock");
        Label userLbl = new Label("Users");
        userLbl.setOnMouseClicked(e -> {
        borderPane.setCenter(tabs.usersTab());
        });
        
        Label logoutLbl = new Label("Logout");
        //logoutLbl.setPadding(new Insets(0,0,0,0));
        leftMenu.getChildren().addAll(stack,supplierLbl,customerLbl,salesLbl,stockLbl,userLbl, logoutLbl);
      
      // borderPane.setStyle("-fx-background-image:url('images/inven.jfif');");
       HBox hbox2=new HBox();
      hbox2.setStyle("-fx-background-color:gray;");
        hbox2.setPadding(new Insets(10,10,10,10));
        hbox2.setSpacing(10);
     
       borderPane.setBottom(hbox2);
        
       scene = new Scene( borderPane,tabs.usersTab().getWidth(), tabs.usersTab().getHeight()); 
       primaryStage.setScene(scene);
     scene.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm()); 
        primaryStage.show();
    }
    
        
    public static void main(String[] args){
       launch(args);
   }
    
}
