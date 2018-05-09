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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
    final ObservableList roleOptions = FXCollections.observableArrayList();
    final ObservableList roleOptionsValue = FXCollections.observableArrayList();
    PreparedStatement pst;
    ResultSet rs;
    
    @Override
    public void start(Stage primaryStage) {
        userComboFill();
         
        BorderPane borderpane=new BorderPane();
        Scene  scene = new Scene(borderpane, 1400,700);
        CheckConnection();
        window=primaryStage;
        window.setTitle("Login Form");
        File file=new File("images/Untitled-1.jpg");
        Image img = new Image(file.toURI().toString(),1400,70,true,true);
        ImageView imv = new ImageView(img);
        
      //  Label iconLbl=new Label("icon pic stays here");
        StackPane iconPane=new StackPane();
        iconPane.getChildren().add(imv);
        iconPane.setStyle("-fx-background-color:white;");
        borderpane.setTop(iconPane);
        
        Label homeLabel=new Label("Welcome message");
        VBox homeBox=new VBox(8);
        homeBox.setStyle("-fx-background-color:#C8F7C5;");
        homeBox.prefWidthProperty().bind(scene.widthProperty().divide(4));
        homeBox.setPadding(new Insets(10, 5, 5, 5));
        homeBox.getChildren().add(homeLabel);
        borderpane.setLeft(homeBox);
       
        
        ComboBox roleCombo = new ComboBox(roleOptions);
        roleCombo.setPromptText("select user role");
        roleCombo.setMaxWidth(220);
        
        nameInput=new TextField();
        nameInput.setMaxWidth(220);
        nameInput.setPromptText("Username");
        passInput=new PasswordField();
        passInput.setMaxWidth(220);
        passInput.setPromptText("Password");
        loginButton = new Button("Login");
        loginButton.setMaxWidth(100);
 
       loginButton.setOnAction(e-> {
           //String role=roleCombo.getSelectionModel().getSelectedItem().toString();
           
            try{
             String query="select UserId, Username,Password,Role from Users where Username=? and Password=?";
             pst=conn.prepareStatement(query);
             pst.setString(1,nameInput.getText());
             pst.setString(2,passInput.getText());
             //pst.setString(1,nameInput.getText());
            // pst.setString(3,roleCombo.getSelectionModel().getSelectedItem().toString());
             rs=pst.executeQuery();
             if(rs.next()){
                 if (rs.getString("Role").equals("admin")) {
                      AdminPage ap=new AdminPage(rs.getInt("UserId"));
                 window.setTitle("Administration");
                 window.setScene(ap.getScene());
                 }
                
                 else{
                 EmployeePage ep=new EmployeePage(rs.getInt("UserId"));
                 window.setTitle("Operator");
                 window.setScene(ep.getScene());
                         }
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
       
      VBox loginVbox=new VBox(8);
      loginVbox.setAlignment(Pos.CENTER);
      
      loginVbox.setPadding(new Insets(10,10,10,10));
      loginVbox.getChildren().addAll(messageLabel,roleCombo,nameInput,passInput,loginButton);
      loginVbox.setStyle("-fx-background-color:#68C3A3;");
       
      StackPane stackPane=new StackPane();
      stackPane.setCenterShape(true);
      stackPane.getChildren().add(loginVbox);
      stackPane.setAlignment(Pos.BASELINE_CENTER);
      borderpane.setCenter(stackPane);
        
        
       
      
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

      public void userComboFill() {
        try {
            conn = DbConnect.getConnection();
            String query = "select UserId,Role from users ";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                roleOptions.add(rs.getString("Role"));
                //roleOptionsValue.add(rs.getString("UserId"));
            }
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
       
    public static void main(String[] args) {
        launch(args);
    }
}
