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
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
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
    static StackPane stackPane;
    public static BorderPane borderpane;
   public static String id;
    @Override
    public void start(Stage primaryStage) {
        DropShadow shadow=new DropShadow();
        borderpane=new BorderPane();
        Scene  scene = new Scene(borderpane, 1500,750);
        CheckConnection();
        window = primaryStage;
        window.setTitle("Login Form");
        File file=new File("images/logo.png");
        Image img = new Image(file.toURI().toString(),1500,100,true,true);
        ImageView imv = new ImageView(img);
        
      //  Label iconLbl=new Label("icon pic stays here");
        StackPane iconPane=new StackPane();
        iconPane.setPadding(new Insets(3,0,3,0));
        iconPane.setStyle("-fx-background-color:rgb(153,153,153);");
        iconPane.getChildren().add(imv);
        //iconPane.setStyle("-fx-background-color:white;");
        borderpane.setTop(iconPane);
        
        Label homeLabel=new Label("Welcome message");
        VBox homeBox=new VBox(8);
        homeBox.setStyle("-fx-background-color:rgb(0,204,204);");
        homeBox.prefWidthProperty().bind(scene.widthProperty().divide(4));
        homeBox.setPadding(new Insets(10, 5, 5, 5));
        homeBox.getChildren().add(homeLabel);
        borderpane.setLeft(homeBox);
        
        
        nameInput=new TextField();
        nameInput.setMaxWidth(220);
        nameInput.setPromptText("Username");
        
        passInput=new PasswordField();
        passInput.setMaxWidth(220);
        passInput.setPromptText("Password");
        Label forgotPassword=new Label("Forgot password?");
        forgotPassword.setStyle("-fx-text-fill:blue;");
        forgotPassword.setOnMouseClicked(e->{
            borderpane.setCenter(new ResetPassword().getScene());
          
        });
        
        loginButton = new Button("Login");
        loginButton.setMaxWidth(100);
        loginButton.setOnMouseEntered(e->{
            loginButton.setEffect(shadow);
        });
        loginButton.setOnMouseExited(e->{
            loginButton.setEffect(null);
        });
        
 
       loginButton.setOnAction(e-> {
           //String role=roleCombo.getSelectionModel().getSelectedItem().toString();
           
            try{
             String query="select *from Users where Username=? and Password=?";
             pst=conn.prepareStatement(query);
             pst.setString(1,nameInput.getText());
             pst.setString(2,passInput.getText());
             
             rs=pst.executeQuery();
             if(rs.next()){
                 id=rs.getString("userId");
                 if (rs.getString("Role").equals("admin")) {
                     if (rs.getString("status").equals("active")) {
                          AdminPage ap=new AdminPage(rs.getInt("UserId"));
                          window.setTitle("Administration");
                          window.setScene(ap.getScene());
                         
                     }
                     else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                          alert.setTitle("Information dialog");
                          alert.setHeaderText(null);
                          alert.setContentText("You have been disabled contact system admin");
                         alert.showAndWait();
                     
                     }
                     
                 }
                
                 else{
                       if (rs.getString("status").equals("active")) {
                              EmployeePage ep=new EmployeePage(rs.getInt("UserId"));
                             window.setTitle("Operator");
                              window.setScene(ep.getScene());
                         
                     }
                       else{
                           Alert alert = new Alert(Alert.AlertType.INFORMATION);
                          alert.setTitle("Information dialog");
                          alert.setHeaderText(null);
                          alert.setContentText("You have been disabled contact system admin");
                         alert.showAndWait();
                       
                       }
                         }
                  System.out.println(id);
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
      loginVbox.getChildren().addAll(messageLabel,nameInput,passInput,forgotPassword,loginButton);
      loginVbox.setStyle("-fx-background-color:rgb(0,153,153);");
       
      stackPane=new StackPane();
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

      /*public void userComboFill() {
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
    }  */
       
    public static void main(String[] args) {
        launch(args);
    }
    
    public Stage getStage(){
        return this.window;
    }
}
