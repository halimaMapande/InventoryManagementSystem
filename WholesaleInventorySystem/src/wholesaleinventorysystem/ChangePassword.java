
package wholesaleinventorysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import static wholesaleinventorysystem.WholesaleInventorySystem.id;




public class ChangePassword  {
PasswordField old,current,confirm;
PreparedStatement pst;
ResultSet rs;
Connection conn;
   
    public TabPane passwordTab() {
        
        TabPane passwordPane = new TabPane();
        Tab passChange = new Tab("Change password");

       Label label=new Label("Change password form");
        old=new PasswordField();
       old.setMaxWidth(220);
       old.setPromptText("Old password");
       
       current=new PasswordField();
       current.setMaxWidth(220);
       current.setPromptText("New password");
       
       confirm=new PasswordField();
       confirm.setMaxWidth(220);
       confirm.setPromptText("Confirm password");
       
       Button btn=new Button("Save changes");
       btn.setOnAction(e->changePassword());
       btn.setStyle("-fx-font-size:14;");
      
       
       
       VBox vbox=new VBox(8);
       vbox.setPadding(new Insets(10,10,10,10));
       vbox.getChildren().addAll(label,old,current,confirm,btn);
       vbox.setAlignment(Pos.CENTER);
       vbox.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm());
       passChange.setContent(vbox);
       
       passwordPane.getTabs().add(passChange);
       return passwordPane;
    }
    
    public void changePassword(){
    try {
        String userId=id;
        String oldPass = old.getText();
        String newPass = current.getText();
        String conPass = confirm.getText();
        
        String query="SELECT * FROM users WHERE Password='"+oldPass+"'";
        conn=DbConnect.getConnection();
        pst=conn.prepareStatement(query);
        rs=pst.executeQuery();
        if(newPass.equals(conPass)){
            while(rs.next()){
            String dbPassword=rs.getString("Password");
            if(dbPassword.equals(oldPass)){
              String sql = "UPDATE users SET Password = ? WHERE userId = ?";
              pst = conn.prepareStatement(sql);
              pst.setString(1,newPass);
              pst.setString(2,userId);
              pst.executeUpdate();
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("PASSWORD HAS BEEN CHANGED");
                alert.showAndWait();
                
                clearFields();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("WRONG OLD PASSWORD");
                alert.showAndWait();
            }
        }

        
        }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("PASSWORD DOES NOT MATCH");
                alert.showAndWait();
        }
    
        
    } catch (SQLException ex) {
        Logger.getLogger(ChangePassword.class.getName()).log(Level.SEVERE, null, ex);
    }
         
         
         
         
    }
    public void clearFields(){
        old.clear();
        current.clear();
        confirm.clear();
    }
    
    }
    

