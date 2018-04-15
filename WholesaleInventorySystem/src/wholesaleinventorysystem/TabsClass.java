package wholesaleinventorysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TabsClass {
    PreparedStatement statement=null;
    Connection conn=null;
    public  TabPane productTab(){
        TabPane productPane = new TabPane();
        
        Tab addTab = new Tab("Add product");
        Tab deleteTab = new Tab("Delete product");
        Tab viewTab = new Tab("View Products");
        
        Label productLbl=new Label("Enter Product details to register");
        TextField nameField=new TextField();
        nameField.setPromptText("Product name");
        TextField descriptionField=new TextField();
        descriptionField.setPromptText("Description");
        TextField buyingPriceField=new TextField();
        buyingPriceField.setPromptText("Buying price");
        TextField sellingPriceField=new TextField();
        sellingPriceField.setPromptText("Selling price");
        Button addProduct=new Button("save");
        
        VBox vbox=new VBox(10);
        vbox.setPadding(new Insets(10,10,10,10));
       vbox.getChildren().addAll(productLbl,nameField,descriptionField,buyingPriceField,sellingPriceField,addProduct);
        addTab.setContent(vbox);   
        
       TableView  table=new TableView<>();
       final ObservableList<ViewProducts> data =FXCollections.observableArrayList();
        TableColumn nameColumn=new TableColumn("Product Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        
        //set column for product prices
        TableColumn descriptionColumn=new TableColumn("Product Description");
        descriptionColumn.setMinWidth(200);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        
        //set column for product quantity
        TableColumn quantityColumn=new TableColumn("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
         TableColumn buyColumn=new TableColumn("Buying Price");
         buyColumn.setMinWidth(100);
         buyColumn.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        
         TableColumn saleColumn=new TableColumn("Selling Price");
         saleColumn.setMinWidth(100);
         saleColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
      
        table.getColumns().addAll(nameColumn,descriptionColumn, quantityColumn, buyColumn,saleColumn);
        
        StackPane stackPane=new StackPane();
         stackPane.setPadding(new Insets(10,10,10,10));
        stackPane.getChildren().add(table);
        viewTab.setContent(stackPane);
        
        productPane.getTabs().addAll(addTab, deleteTab,viewTab);
        table.setItems(data);
        
       return productPane;
    }
    
    
      public  TabPane usersTab(){
   TabPane userPane = new TabPane();
   Tab addUser = new Tab("Add user");
   Tab deleteUser = new Tab("Delete user");
   Tab viewUser = new Tab("View user");
   
   Label  fNameLabel = new Label("First Name");
   TextField fNameField = new TextField();
   Label lNameLabel=new Label("Last name");      
   TextField lNameField=new TextField() ; 
   Label  phoneLabel=new Label("Phone number");
   TextField phoneField=new TextField();
   Label emailLabel=new Label("Email");
   TextField emailField=new TextField();
   Label userLabel=new Label("Username");
   TextField userField=new TextField();
   Label passLabel=new Label("Password");
   TextField passField=new PasswordField();
   Label roleLabel=new Label("User role");
   TextField roleField=new TextField();
   Button saveButton=new Button("Save"); 
    saveButton.setOnAction(e->{
        
                  try{
             String query="INSERT INTO Users(FirstName,LastName,PhoneNumber,Email,Username,Password,Role) VALUES(?,?,?,?,?,?,?)";
             conn = DbConnect.getConnection();
             statement=conn.prepareStatement(query);
             statement.setString(1,fNameField.getText());
             statement.setString(2,lNameField.getText());
             statement.setString(3,phoneField.getText());
             statement.setString(4,emailField.getText());
             statement.setString(5,userField.getText());
             statement.setString(6,passField.getText());
             statement.setString(7,roleField.getText());
             statement.execute();
             Alert alert=new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Information dialog");
             alert.setHeaderText(null);
             alert.setContentText("User has been created");
             alert.showAndWait();
            // clearFields();
         }
         catch(Exception ex){
             System.err.println(ex.toString());
         }
         finally{
             try{
            statement.close();
             conn.close();
         }
             catch(Exception ex){}
             
         }
     });
        
     GridPane gridPane = new GridPane();
     gridPane.setPadding(new Insets(10,5,5,5));
     gridPane.setAlignment(Pos.CENTER);   
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        //add to layout gridpane
   gridPane.add(fNameLabel,0,0,1,1);
   gridPane.add(fNameField,1,0,1,1);
   gridPane.add(lNameLabel,0,1,1,1); 
   gridPane.add(lNameField,1,1,1,1); 
   gridPane.add(phoneLabel,0,2,1,1);
   gridPane.add(phoneField,1,2,1,1);
   gridPane.add(emailLabel,0,3,1,1);
   gridPane.add(emailField,1,3,1,1);
   gridPane.add(userLabel,0,4,1,1);
   gridPane.add(userField,1,4,1,1); 
   gridPane.add(passLabel,0,5,1,1);
   gridPane.add(passField,1,5,1,1);
   gridPane.add(roleLabel,0,6,1,1);
   gridPane.add(roleField,1,6,1,1); 
   gridPane.add(saveButton,1,7,1,1); 
        
   addUser.setContent(gridPane);
   userPane.getTabs().addAll(addUser, deleteUser, viewUser);
   return userPane;
    }
}
