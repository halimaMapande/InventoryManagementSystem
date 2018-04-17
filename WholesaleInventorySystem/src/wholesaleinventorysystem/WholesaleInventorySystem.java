/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

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
import javafx.scene.layout.GridPane;
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
       
        CheckConnection();
        window=primaryStage;
        window.setTitle("Login Form");
       
        //creating layout
         GridPane gridPane = new GridPane();
         gridPane.setPadding(new Insets(10,10,10,10));
         gridPane.setVgap(8);
         gridPane.setHgap(8);
        
         //control
         
        nameLabel=new Label("username");
        nameInput=new TextField();
        passLabel=new Label("password");
        passInput=new PasswordField();
        loginButton = new Button("Login");
 
       loginButton.setOnAction(e-> {
           PreparedStatement pst;
           ResultSet rs;
            try{
             String query="select * from Users where Username=? and Password=? ";
             pst=conn.prepareStatement(query);
             pst.setString(1,nameInput.getText());
             pst.setString(2,passInput.getText());
             rs=pst.executeQuery();
             if(rs.next()){
                 //window.close();
                 AdminPage ap=new AdminPage();
                 window.setTitle("Administration");
                 window.setScene(ap.getScene());
                //window = ap.getStage();
                // if(rs.getString("Role").equals("admin")){
               // messageLabel.setText("Login successful");
               // nameInput.clear();
               //  }
                /* else {
                 Users us = new Users();
                 us.start(primaryStage);
                 }*/
                
             }
             else{
                 messageLabel.setText("Login failed");
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
        gridPane.add(nameLabel, 0, 0, 1, 1);
        gridPane.add(nameInput, 1, 0, 1, 1);
        gridPane.add(passLabel, 0,1, 1, 1);
        gridPane.add(passInput, 1, 1, 1, 1);
        gridPane.add(loginButton, 1, 2, 1, 1);
        gridPane.add(messageLabel, 0, 3, 2, 1);
      
     gridPane.setStyle("-fx-background-color:cyan;");
       
       
      Scene scene = new Scene(gridPane, 700,500);
       window.setScene(scene);
      // scene.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm()); 
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
