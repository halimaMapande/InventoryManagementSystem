/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author evod
 */
public class WholesaleInventorySystem extends Application {
    Stage window;
    Button loginButton;
    Label nameLabel,passLabel,messageLabel;
    TextField nameInput;
    PasswordField passInput;
    Connection conn;
    @Override
    public void start(Stage primaryStage) {
        
         
        BorderPane borderpane=new BorderPane();
        Scene  scene = new Scene(borderpane, 1400,700);
        CheckConnection();
        window=primaryStage;
        window.setTitle("Login Form");
        File file=new File("images/Untitled-1.jpg");
        Image img = new Image(file.toURI().toString(),1000,200,true,true);
        ImageView imv = new ImageView(img);
        
      //  Label iconLbl=new Label("icon pic stays here");
        StackPane iconPane=new StackPane();
        iconPane.getChildren().add(imv);
        //iconPane.setStyle("-fx-background-color:gray;");
        borderpane.setTop(iconPane);
        
        Label homeLabel=new Label("Welcome message");
        VBox homeBox=new VBox(8);
        homeBox.setStyle("-fx-background-color:cyan;");
        homeBox.prefWidthProperty().bind(scene.widthProperty().divide(4));
        homeBox.setPadding(new Insets(10, 5, 5, 5));
        homeBox.getChildren().add(homeLabel);
        borderpane.setLeft(homeBox);
       
        //creating layout
         GridPane gridPane = new GridPane();
         gridPane.setPadding(new Insets(300,50,50,300));
         gridPane.setVgap(10);
         gridPane.setHgap(10);
        
         //control
         
        nameLabel=new Label("Username");
        nameLabel.setStyle("-fx-text-fill:white;");
        nameInput=new TextField();
        passLabel=new Label("Password");
        passLabel.setStyle("-fx-text-fill:white;");
        passInput=new PasswordField();
        loginButton = new Button("Login");
        loginButton.setMaxWidth(100);
 
       loginButton.setOnAction(e-> {
           PreparedStatement pst;
           ResultSet rs;
            try{
             String query="select UserId, Username,Password,Role from Users where Username=? and Password=? ";
             pst=conn.prepareStatement(query);
             pst.setString(1,nameInput.getText());
             pst.setString(2,passInput.getText());
             rs=pst.executeQuery();
             if(rs.next()){
                 AdminPage ap=new AdminPage(rs.getInt("UserId"));
                 window.setTitle("Administration");
                 window.setScene(ap.getScene());
               //if(nameInput.equals("Username") && passInput.equals("Password") && admin.equals("Role"))
                // if(rs.getString("Role").equals("admin")){
               // messageLabel.setText("Login successful");
               // nameInput.clear();
               // }
                // else {
                 //Users us = new Users();
                 //us.start(primaryStage);
                 //}
                
             }
             else{
                 messageLabel.setText("Wrong username or password please enter your correct username and password!");
                 messageLabel.setStyle("-fx-text-fill:red");
                      passInput.clear();
             }
             pst.close();
             rs.close();
        
            }
            catch(Exception ex){
                messageLabel.setText("SQL error");
                System.err.println(ex.toString());
            }
            
       });
        
        messageLabel=new Label();
       
       //setting constraints
        gridPane.add(messageLabel, 0, 0, 3, 1);
        gridPane.add(nameLabel, 0, 1, 1, 1);
        gridPane.add(nameInput, 1, 1, 1, 1);
        gridPane.add(passLabel, 0,2, 1, 1);
        gridPane.add(passInput, 1, 2, 1, 1);
        gridPane.add(loginButton, 1, 3, 1, 1);
        
      
        gridPane.setStyle("-fx-background-color:rgb(0,51,102);");
        borderpane.setCenter(gridPane);
       
      
       window.setScene(scene);
       scene.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm()); 
       window.show();
    }
       public void CheckConnection(){
        conn=DbConnect.getConnection();
        if(conn==null){
            System.out.println("Connection not succsessful..!");
            System.exit(1);
        }
        else
             System.out.println("Connection succsessful..!");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
