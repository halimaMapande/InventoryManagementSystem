/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author evod
 */
public class TestClass extends Application {
    Stage window;
    Label nameLabel,phoneLabel,emailLabel,addLabel;
    TextField nameField,phoneField,emailField,addField;
    Button submitButton,viewButton;
    TableView<ViewSuppliers> table;
    Connection conn=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
    @Override
    public void start(Stage primaryStage)throws Exception {
       CheckConnection();
     window=primaryStage;
     window.setTitle("Supplier register");
    BorderPane border=new BorderPane();
      GridPane layout = new GridPane();
     //set components
     viewButton=new Button("View suppliers");
     
     BorderPane.setAlignment(viewButton,Pos.TOP_RIGHT);
     
     nameLabel=new Label("Supplier name");
     nameField=new TextField();
     phoneLabel=new Label("Phone number");
     phoneField=new TextField();
     emailLabel=new Label("Email");
     emailField=new TextField();
     addLabel=new Label("Address");
     addField=new TextField();
     submitButton=new Button("Save");
        //Layout
        
    layout.add(nameLabel,0,0,1,1);
    layout.add(nameField,1,0,1,1);
    layout.add(phoneLabel,0,1,1,1); 
    layout.add(phoneField,1,1,1,1); 
    layout.add(emailLabel,0,2,1,1);
    layout.add(emailField,1,2,1,1);
    layout.add(addLabel,0,3,1,1);
    layout.add(addField,1,3,1,1);
    layout.add(submitButton,1,4,1,1);
    
    submitButton.setOnAction(e->{
        try{
             String query="INSERT INTO Supplier(SupplierName,PhoneNumber,Email,Address) VALUES(?,?,?,?)";
             pst=conn.prepareStatement(query);
             pst.setString(1,nameField.getText());
             pst.setString(2,phoneField.getText());
             pst.setString(3,emailField.getText());
             pst.setString(4,addField.getText());
             pst.execute();
             Alert alert=new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Information dialog");
             alert.setHeaderText(null);
             alert.setContentText("Supplier has been registered");
             alert.showAndWait();
             clearFields();
         }
         catch(Exception ex){
             System.err.println(ex);
         }
         finally{
             try{
             pst.close();
             conn.close();
         }
             catch(Exception ex){}
             
         }
        
    });
    
  layout.setPadding(new Insets(10,10,10,10));
    layout.setVgap(8);
    layout.setHgap(8);
   
    table=new TableView<>();
    final ObservableList<ViewSuppliers> data =FXCollections.observableArrayList();
        TableColumn nameColumn=new TableColumn("Supplier Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        //set column for product prices
        TableColumn phoneColumn=new TableColumn("Phone");
        phoneColumn.setMinWidth(200);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        //set column for product quantity
        TableColumn emailColumn=new TableColumn("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
         TableColumn addColumn=new TableColumn("Address");
       addColumn.setMinWidth(200);
        addColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
      
        table.getColumns().addAll(nameColumn,phoneColumn, emailColumn,addColumn);
        
        viewButton.setOnAction(e->{
         try{
             String query="select * from Supplier";
             pst=conn.prepareStatement(query);
             rs=pst.executeQuery();
             while(rs.next()){
                // data.add(new ViewSuppliers(
                   // rs.getString("SupplierName"),
                  //  rs.getString("PhoneNumber"),
                  //  rs.getString("Email"),
                  //  rs.getString("Address")
               //  ));
                 table.setItems(data);
             }
             pst.close();
             pst.close();
         }
         catch(Exception ex1){
             System.err.println(ex1);
         }
     });
        
     border.setTop(viewButton);
     border.setLeft(layout);
     border.setRight(table);
    
     Scene scene = new Scene(border);
     window.setScene(scene);
     //scene.getStylesheets().add(getClass().getResource("LoginStyleSheet.css").toExternalForm());
     window.show();
    }
    
    
    public static void main(String [] args){
        launch(args);
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

     public void clearFields() {
      nameField.clear();
      phoneField.clear();
      emailField.clear();
      addField.clear();
     }
  
    }

   
    

