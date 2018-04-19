package wholesaleinventorysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    final ObservableList option=FXCollections.observableArrayList();
    public  TabPane productTab(){
        TabPane productPane = new TabPane();
        
        Tab addTab = new Tab("Add product");
        Tab deleteTab = new Tab("Delete product");
        Tab viewTab = new Tab("View Products");
        
        Label productLbl=new Label("Enter product details to register");
        productLbl.setStyle("-fx-text-fill:white;");
         ComboBox supplierCombo=new ComboBox(option);
         //supplierBox.set();
                
        
    //========================================================

    //========================================================
        supplierCombo.setPromptText("Select supplier ");
        supplierCombo.setMaxWidth(220);
   
        supplierCombo.setEditable(true);
        TextField nameField=new TextField();
        nameField.setPromptText("Product name");
        nameField.setMaxWidth(220);
        TextField descriptionField=new TextField();
        descriptionField.setPromptText("Description");
        descriptionField.setMaxWidth(220);
        TextField buyingPriceField=new TextField();
        buyingPriceField.setPromptText("Buying price");
         buyingPriceField.setMaxWidth(220);
        TextField sellingPriceField=new TextField();
        sellingPriceField.setPromptText("Selling price");
        sellingPriceField.setMaxWidth(220);
        Button addProduct=new Button("save");
        
        addProduct.setOnAction(e->{
        
                  try{
             String query="INSERT INTO Product(ProductName,ProductDescription,BuyingPrice,SellingPrice) VALUES(?,?,?,?)";
             conn = DbConnect.getConnection();
             statement=conn.prepareStatement(query);
              statement.setString(1,supplierCombo.getSelectionModel().getSelectedItem().toString());
             statement.setString(2,nameField.getText());
             statement.setString(3,descriptionField.getText());
             statement.setString(4,buyingPriceField.getText());
             statement.setString(5,sellingPriceField.getText());
             statement.execute();
             Alert alert=new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Information dialog");
             alert.setHeaderText(null);
             alert.setContentText("Supplier has been registered");
             alert.showAndWait();
            // clearFields();
         }
         catch(Exception ex){
             System.err.println("product Error: \n"+ex.toString());
         }
         finally{
             try{
            statement.close();
             conn.close();
         }
             catch(Exception ex){}
             
         }
     });
        
        
        
        VBox vbox=new VBox(10);
        vbox.setPadding(new Insets(10,10,10,10));
       vbox.getChildren().addAll(productLbl,supplierCombo,nameField,descriptionField,buyingPriceField,sellingPriceField,addProduct);
       vbox.setAlignment(Pos.CENTER); 
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
//==================================================================
   /* public void comboboxdata(){
        ComboBox supplierCombo=new ComboBox();
        try{
            conn = DbConnect.getConnection();
            String sql1 =" select SullierId,SupplierName from Supplier";
            statement = conn.prepareStatement(sql1);
            ResultSet rs = statement.executeQuery(sql1);
            while(rs.next()){
            supplierCombo.add(rs.getString("SupplierName"));
            
            }
        }catch(SQLException ex){
        
        }

    }*/
//==================================================================    
      public  TabPane usersTab(){
   TabPane userPane = new TabPane();
 
   Tab addUser = new Tab("Create user account");
   Tab deleteUser = new Tab("Delete user");
   Tab viewUser = new Tab("View users");
   
   Label  fNameLabel = new Label("First Name");
   fNameLabel.setStyle("-fx-text-fill:white;");
   TextField fNameField = new TextField();
   Label lNameLabel=new Label("Last name");
   lNameLabel.setStyle("-fx-text-fill:white;");
   TextField lNameField=new TextField() ; 
   Label  phoneLabel=new Label("Phone number");
   phoneLabel.setStyle("-fx-text-fill:white;");
   TextField phoneField=new TextField();
   Label emailLabel=new Label("Email");
   emailLabel.setStyle("-fx-text-fill:white;");
   TextField emailField=new TextField();
   Label userLabel=new Label("Username");
   userLabel.setStyle("-fx-text-fill:white;");
   TextField userField=new TextField();
   Label passLabel=new Label("Password");
   passLabel.setStyle("-fx-text-fill:white;");
   TextField passField=new PasswordField();
   Label roleLabel=new Label("User role");
   roleLabel.setStyle("-fx-text-fill:white;");
   ComboBox roleCombo=new ComboBox();
        roleCombo.getItems().addAll(
          "admin",
          "operator"
        );
    
        roleCombo.setPromptText("Select user role");
   
        roleCombo.setEditable(true);
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
             statement.setString(7,roleCombo.getSelectionModel().getSelectedItem().toString());
             statement.execute();
             Alert alert=new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Information dialog");
             alert.setHeaderText(null);
             alert.setContentText("User has been created");
             alert.showAndWait();
            // clearFields();
         }
         catch(Exception ex){
             System.err.println("Error: \n"+ex.toString());
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
        gridPane.setVgap(8);
        
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
   gridPane.add(roleCombo,1,6,1,1); 
   gridPane.add(saveButton,1,7,1,1); 
        
   addUser.setContent(gridPane);
   userPane.getTabs().addAll(addUser, deleteUser, viewUser);
   return userPane;
    }
      
      
       public  TabPane suppliersTab(){
   TabPane supplierPane = new TabPane();
   Tab addSupplier = new Tab("Add supplier");
   Tab viewSupplier = new Tab("View suppliers");
   
   Label supplierLbl=new Label("Enter Suppliers details to register");
   supplierLbl.setStyle("-fx-text-fill:white;");
        TextField supplierNameField=new TextField();
        supplierNameField.setMaxWidth(220);
        supplierNameField.setPromptText("Supplier name");
        TextField phoneField=new TextField();
        phoneField.setMaxWidth(220);
        phoneField.setPromptText("Phone number");
        TextField emailField=new TextField();
        emailField.setMaxWidth(220);
        emailField.setPromptText("Email address");
        TextField addressField=new TextField();
        addressField.setMaxWidth(220);
        addressField.setPromptText("Address");
        Button addSuppliers=new Button("save");
        
        addSuppliers.setOnAction(e->{
           String phone=phoneField.getText();
        if(valPhone(phone)){
              try{
             String query="INSERT INTO Supplier(SupplierName,PhoneNumber,Email,Address) VALUES(?,?,?,?)";
             conn = DbConnect.getConnection();
             statement=conn.prepareStatement(query);
             statement.setString(1,supplierNameField.getText());
             statement.setString(2,phone);
             statement.setString(3,emailField.getText());
             statement.setString(4,addressField.getText());
             statement.execute();
             Alert alert=new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Information dialog");
             alert.setHeaderText(null);
             alert.setContentText("Supplier has been registered");
             alert.showAndWait();
            // clearFields();
         }
         catch(Exception ex){
             System.err.println("supplier Error: \n"+ex.toString());
         }
         finally{
             try{
            statement.close();
             conn.close();
         }
             catch(Exception ex){}
             
         }
        }
        else{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information dialog");
           alert.setHeaderText(null);
            alert.setContentText("invalid phone number");
            alert.showAndWait();
        }
                
     });
        
        
        VBox vbox1=new VBox(10);
        vbox1.setPadding(new Insets(10,10,10,10));
       vbox1.getChildren().addAll(supplierLbl,supplierNameField,phoneField,emailField,addressField,addSuppliers);
       vbox1.setAlignment(Pos.CENTER); 
       addSupplier.setContent(vbox1);   
       
       Label searchLbl=new Label("Supplier name: ");
       searchLbl.setStyle("-fx-text-fill:white;");
       TextField search=new TextField();
       Button search1=new Button("seacrh");
       search1.setStyle("-fx-text-fill:white;");
       
        GridPane searchPane=new GridPane();
        searchPane.setPadding(new Insets(10,10,10,10));
        searchPane.setAlignment(Pos.CENTER);
        searchPane.setHgap(8);
        searchPane.setVgap(8);
        searchPane.add(searchLbl,0,0);
        searchPane.add(search,1,0);
        searchPane.add(search1,2,0);
        
        
        //create table for viewing suppliers from the database
        TableView  suppliers=new TableView<>();
        final ObservableList<ViewSuppliers> data =FXCollections.observableArrayList();
        
        //create column supplier name to diplay names of suppliers registered in the database
        TableColumn snameColumn=new TableColumn("Supplier Name");
        snameColumn.setMinWidth(200);
        snameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        
        //set column for suppliers phone numbers
        TableColumn phoneColumn=new TableColumn("Phone number");
        phoneColumn.setMinWidth(150);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        
        //set column for displaying suppliers email
        TableColumn emailColumn=new TableColumn("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        //set column for displaying suppliers adress
         TableColumn addressColumn=new TableColumn("Address");
         addressColumn.setMinWidth(200);
         addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        
         //add all columns to the table
        suppliers.getColumns().addAll(snameColumn,phoneColumn, emailColumn, addressColumn);
        suppliers.setItems(data);
        
        //set layout
        VBox vbox2=new VBox(8);
        vbox2.setPadding(new Insets(10,10,10,10));
        vbox2.getChildren().addAll(searchPane,suppliers);
        viewSupplier.setContent(vbox2);
       
        //adding tabs to tabpane layout
        supplierPane.getTabs().addAll(addSupplier, viewSupplier);
        return supplierPane;
       }
       
      public  TabPane empSalesTab(){
        TabPane empSalesPane = new TabPane();
        Tab addSales = new Tab("Add sales");
        Tab viewSales = new Tab("View sales");
   
        empSalesPane.getTabs().addAll(addSales, viewSales);
        return empSalesPane;
      }
      
       public  TabPane customersTab(){
        TabPane customersPane = new TabPane();
        Tab addCustomer = new Tab("Add customer");
        Tab viewCustomer = new Tab("View transactions");
        //Add content to add customer tab
        Label addCustomers=new Label("Enter customer details here.");
        addCustomers.setStyle("-fx-text-fill:white;");
        
        TextField fName=new TextField();
        fName.setPromptText("First Name");
        fName.setMaxWidth(220);
        
        TextField lName=new TextField();
        lName.setPromptText("Last Name");
        lName.setMaxWidth(220);
        
        TextField email=new TextField();
        email.setPromptText("Email");
        email.setMaxWidth(220);
        
        TextField phone=new TextField();
        phone.setPromptText("Phone Number");
        phone.setMaxWidth(220);
        
        Button submitButton=new Button("Save");
        
        VBox registerBox=new VBox(8);
        registerBox.setAlignment(Pos.CENTER);
        registerBox.getChildren().addAll(addCustomers,fName,lName,email,phone,submitButton);
        addCustomer.setContent(registerBox);
        
        //set content to view transactions
        Label searchLbl=new Label("Customer Name");
        searchLbl.setStyle("-fx-text-fill:white;");
        ComboBox comboBox=new ComboBox();
        comboBox.getItems().addAll(
          "Sonia Scarlet",
          "Nasra Nymph",
          "Sameer Twaha",
          "Nasri Ibrahim"
        );
    
        comboBox.setPromptText("Select customer");
   
        comboBox.setEditable(true);
        Button searchButton=new Button("search");
        
        GridPane searchPane=new GridPane();
        searchPane.setPadding(new Insets(10,10,10,10));
        searchPane.setAlignment(Pos.CENTER);
        searchPane.setHgap(8);
        searchPane.setVgap(8);
        
        searchPane.add(searchLbl, 0, 0);
        searchPane.add(comboBox, 1, 0);
        searchPane.add(searchButton, 2, 0);
        
        //create table to display transaction for a selected customer
        TableView  transTable=new TableView<>();
        final ObservableList<ViewCustomers> data =FXCollections.observableArrayList();
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
        
         TableColumn priceColumn=new TableColumn("Price");
         priceColumn.setMinWidth(100);
         priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricerice"));
      
         //add all columns to the table
        transTable.getColumns().addAll(nameColumn,descriptionColumn, quantityColumn, priceColumn);
        transTable.setItems(data);
        
        //setting layout
        VBox viewbox=new VBox(8);
        viewbox.setPadding(new Insets(10,10,10,10));
        viewbox.getChildren().addAll(searchPane,transTable);
        viewCustomer.setContent(viewbox);
  
        
        customersPane.getTabs().addAll(addCustomer, viewCustomer);
        return customersPane;
      }
       //=====================phone number validation========================================

       
       public static boolean valPhone(String in){
       return in.charAt(0)=='0' && in.length()==10 && in.matches("[0-9]+");
       }
}
