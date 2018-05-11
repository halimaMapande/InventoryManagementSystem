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
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import static wholesaleinventorysystem.ResetPassword.add;

/**
 *
 * @author josephine
 */
public class RecoverPassword  {
    PasswordField txt1,txt2;
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    public VBox getScene() {
       Label label=new Label("Enter your phonenumber used to register");
       txt1=new PasswordField(); 
       txt1.setMaxWidth(220);
       txt2=new PasswordField();
       txt2.setMaxWidth(220);
       
       Button btn=new Button("submit");
       btn.setStyle("-fx-font-size:14;");
       btn.setOnAction(e->changePassword());
       
       VBox vbox=new VBox(8);
       vbox.setPadding(new Insets(10,10,10,10));
       vbox.getChildren().addAll(label,txt1,txt2,btn);
       vbox.setAlignment(Pos.CENTER);
       vbox.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm()); 
        
       return vbox;
    }
private void changePassword(){
    String userId = add;
    String newpassword = txt1.getText();
    String cnfmpassword = txt2.getText();
    
     if(newpassword.equals(cnfmpassword)){
        try {
            conn = DbConnect.getConnection();
            String sql = "UPDATE users SET Password =? WHERE userId =?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,newpassword);
            pst.setString(2,userId);
            pst.executeUpdate();
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("PASSWORD HAS BEEN RESET");
                alert.showAndWait();
                WholesaleInventorySystem.borderpane.setCenter(WholesaleInventorySystem.stackPane);
                
        } catch (SQLException ex) {
            Logger.getLogger(RecoverPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
     }else{
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("PASSWORD DOES NOT MATCH");
                alert.showAndWait();
     }
}
    
    
}
