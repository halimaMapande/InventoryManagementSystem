/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 *
 * @author josephine
 */
public class ResetPassword {
    TextField txt=new TextField();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    Scene scene;
    Stage window;
    public VBox getScene() {
       Label label=new Label("Enter your phonenumber used to register");
        
       txt.setMaxWidth(220);
       Button btn=new Button("submit");
       btn.setStyle("-fx-font-size:14;");
       btn.setOnAction(e->checkPhonenumber());
       
       
       VBox vbox=new VBox(8);
       vbox.setPadding(new Insets(10,10,10,10));
       vbox.getChildren().addAll(label,txt,btn);
       vbox.setAlignment(Pos.CENTER);
       vbox.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm()); 
       return vbox;
    }


    public static String add;
    private void checkPhonenumber(){
        try {
            String phone = txt.getText();
            conn = DbConnect.getConnection();
            String sql = "SELECT * FROM users WHERE PhoneNumber='"+phone+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
               if(rs.next()){
                add = rs.getString("userId");
               
                WholesaleInventorySystem.borderpane.setCenter(new RecoverPassword().getScene());
               
               }else{
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("PHONE NUMBER NOT FOUND");
                alert.showAndWait();
               }   
            
        } catch (SQLException ex) {
            Logger.getLogger(ResetPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
