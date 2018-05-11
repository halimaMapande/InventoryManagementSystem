/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
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
public class ResetPassword extends Application  {
    TextField txt=new TextField();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    Scene scene;
    Stage window;
    @Override
    public void start(Stage primaryStage) {
        window=primaryStage;
       Label label=new Label("Enter your phonenumber used to register");
        
       txt.setMaxWidth(220);
       Button btn=new Button("submit");
       btn.setStyle("-fx-font-size:14;");
       btn.setOnAction(e->checkPhonenumber());
       
       
       VBox vbox=new VBox(8);
       vbox.setPadding(new Insets(10,10,10,10));
       vbox.getChildren().addAll(label,txt,btn);
       vbox.setAlignment(Pos.CENTER);
       scene = new Scene(vbox, 1400, 700);
       scene.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm()); 
        
       primaryStage.setScene(scene);
       primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
   // public static void main(String[] args) {
    //    launch(args);
   // }
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
               
               RecoverPassword rcp=new RecoverPassword();
               window.setTitle("Password reset");
                window.setScene(rcp.getScene());
               
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
