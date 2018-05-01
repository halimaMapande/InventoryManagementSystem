package wholesaleinventorysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TabsClass {
    
    PreparedStatement statement = null;
    ResultSet rs;
    Connection conn = null;
    final ObservableList options = FXCollections.observableArrayList();
    final ObservableList optionValue = FXCollections.observableArrayList();
    final ObservableList list = FXCollections.observableArrayList();
    final ObservableList customerlist = FXCollections.observableArrayList();
    final ObservableList productlist = FXCollections.observableArrayList();
    final ObservableList customerlistValue = FXCollections.observableArrayList();
    final ObservableList productlistValue = FXCollections.observableArrayList();
    int userId;
    int latestSalesId;
     
    public TabsClass(int userId) {
        this.userId = userId;
    }
<<<<<<< HEAD
    //**********************************************************************************************
    public TabPane productTab() {
        productComboFill();
        TabPane productPane = new TabPane();
        
        TableView<ViewProducts> productTable = new TableView<>();
        final ObservableList<ViewProducts> productData = FXCollections.observableArrayList();

        Tab addTab = new Tab("Add product");
        //Tab deleteTab = new Tab("Delete product");
        Tab viewTab = new Tab("View Products");

        Label productLbl = new Label("Enter product details to register");
        productLbl.setStyle("-fx-text-fill:white;");
        ComboBox supplierCombo = new ComboBox(options);

        supplierCombo.setPromptText("Select supplier ");
        supplierCombo.setMaxWidth(220);

        //supplierCombo.setEditable(true);
        nameField = new TextField();
        nameField.setPromptText("Product name");
        nameField.setMaxWidth(220);
        descriptionField = new TextField();
        descriptionField.setPromptText("Description");
        descriptionField.setMaxWidth(220);
        buyingPriceField = new TextField();
        buyingPriceField.setPromptText("Buying price");
        buyingPriceField.setMaxWidth(220);
        sellingPriceField = new TextField();
        sellingPriceField.setPromptText("Selling price");
        sellingPriceField.setMaxWidth(220);
        Button addProduct = new Button("save");
        addProduct.setMaxWidth(100);
        addProduct.setStyle("-fx-font-size:16");
        addProduct.setOnAction(e -> {
            try {
                String query = "INSERT INTO Product(ProductName,ProductDescription,BuyingPrice,SellingPrice,SupplierId) VALUES(?,?,?,?,?)";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                String supplierName = supplierCombo.getSelectionModel().getSelectedItem().toString();
                int supplierValue = options.indexOf(supplierName);
                int id = new Integer(optionValue.get(supplierValue).toString());
                statement.setString(1, nameField.getText());
                statement.setString(2, descriptionField.getText());
                statement.setString(3, buyingPriceField.getText());
                statement.setString(4, sellingPriceField.getText());
                statement.setInt(5, id);
                statement.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("Product is successfulregistered");
                alert.showAndWait();
                 clearFields();
            } catch (Exception ex) {
                System.err.println("product Error: \n" + ex.toString());
            } finally {
                try {
                    statement.close();
                    conn.close();
                } catch (Exception ex) {
                }

            }
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(productLbl, supplierCombo, nameField, descriptionField, buyingPriceField, sellingPriceField, addProduct);
        vbox.setAlignment(Pos.CENTER);
        addTab.setContent(vbox);

        /*Label searchProduct=new Label("Product name: ");
        searchProduct.setStyle("-fx-text-fill:white;");
        
        TextField search=new TextField();
        
        Button search1=new Button("seacrh");
        search1.setStyle("-fx-text-fill:white;");
         */
        Button viewButton = new Button("click to view products");
        viewButton.setStyle("-fx-text-fill:white;");

         viewButton.setOnAction(e -> {
            try {
                String query = "select *from Product";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                rs = statement.executeQuery();
                while (rs.next()) {
                    productData.add(new ViewProducts(
                            rs.getString("ProductName"),
                            rs.getString("ProductDescription"),
                            rs.getInt("BuyingPrice"),
                            rs.getInt("SellingPrice")
                            
                    ));
                    productTable.setItems(productData);
                }
                statement.close();
                rs.close();
                //conn.close();
            } 
            catch (Exception ex1) {
                System.err.println(ex1);
            }
        });
        
        Button deleteButton = new Button("delete");
        deleteButton.setStyle("-fx-text-fill:white;");

        GridPane viewPane = new GridPane();
        viewPane.setPadding(new Insets(10, 10, 10, 10));
        viewPane.setHgap(10);
        viewPane.setVgap(10);

        viewPane.add(viewButton, 0, 0);
        viewPane.add(deleteButton, 1, 0);

        
        TableColumn nameColumn = new TableColumn("Product Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        //set column for product prices
        TableColumn descriptionColumn = new TableColumn("Product Description");
        descriptionColumn.setMinWidth(200);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("productDescription"));

        //set column for product quantity
       /* TableColumn quantityColumn = new TableColumn("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));*/

        TableColumn buyColumn = new TableColumn("Buying Price");
        buyColumn.setMinWidth(100);
        buyColumn.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));

        TableColumn saleColumn = new TableColumn("Selling Price");
        saleColumn.setMinWidth(100);
        saleColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        productTable.getColumns().addAll(nameColumn, descriptionColumn, buyColumn, saleColumn);

       

        VBox centerMenu = new VBox(8);
        centerMenu.setPadding(new Insets(10, 10, 10, 10));
        centerMenu.getChildren().addAll(viewPane, productTable);
        viewTab.setContent(centerMenu);

        productPane.getTabs().addAll(addTab, viewTab);

        return productPane;
    }
//**********************************************************************************************************

    public TabPane usersTab() {
        TabPane userPane = new TabPane();

        Tab addUser = new Tab("Create user account");
        Tab deleteUser = new Tab("Delete user");
        Tab viewUser = new Tab("View users");

        TableView<ViewUsers> usersTable = new TableView<>();
        final ObservableList<ViewUsers> usersData = FXCollections.observableArrayList();

        Label fNameLabel = new Label("First Name");
        fNameLabel.setStyle("-fx-text-fill:white;");
        fNameField = new TextField();
        Label lNameLabel = new Label("Last name");
        lNameLabel.setStyle("-fx-text-fill:white;");
        lNameField = new TextField();
        Label phoneLabel = new Label("Phone number");
        phoneLabel.setStyle("-fx-text-fill:white;");
        phoneField = new TextField();
        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-text-fill:white;");
        emailField = new TextField();
        Label userLabel = new Label("Username");
        userLabel.setStyle("-fx-text-fill:white;");
        userField = new TextField();
        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-text-fill:white;");
        passField = new PasswordField();
        Label roleLabel = new Label("User role");
        roleLabel.setStyle("-fx-text-fill:white;");
        ComboBox roleCombo = new ComboBox();
        roleCombo.getItems().addAll(
                "admin",
                "operator"
        );

        roleCombo.setPromptText("Select user role");

        roleCombo.setEditable(true);
        Button saveUserButton = new Button("Save");
        saveUserButton.setMaxWidth(100);
        saveUserButton.setStyle("-fx-font-size:16");
        saveUserButton.setOnAction(e -> {

            try {
                String query = "INSERT INTO Users(FirstName,LastName,PhoneNumber,Email,Username,Password,Role) VALUES(?,?,?,?,?,?,?)";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                statement.setString(1, fNameField.getText());
                statement.setString(2, lNameField.getText());
                statement.setString(3, phoneField.getText());
                statement.setString(4, emailField.getText());
                statement.setString(5, userField.getText());
                statement.setString(6, passField.getText());
                statement.setString(7, roleCombo.getSelectionModel().getSelectedItem().toString());
                statement.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("User has been created");
                alert.showAndWait();
                clearFields();
            } catch (Exception ex) {
                System.err.println("Error: \n" + ex.toString());
            } finally {
                try {
                    statement.close();
                    conn.close();
                } catch (Exception ex) {
                }

            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 5, 5, 5));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(8);

        //add to layout gridpane
        gridPane.add(fNameLabel, 0, 0, 1, 1);
        gridPane.add(fNameField, 1, 0, 1, 1);
        gridPane.add(lNameLabel, 0, 1, 1, 1);
        gridPane.add(lNameField, 1, 1, 1, 1);
        gridPane.add(phoneLabel, 0, 2, 1, 1);
        gridPane.add(phoneField, 1, 2, 1, 1);
        gridPane.add(emailLabel, 0, 3, 1, 1);
        gridPane.add(emailField, 1, 3, 1, 1);
        gridPane.add(userLabel, 0, 4, 1, 1);
        gridPane.add(userField, 1, 4, 1, 1);
        gridPane.add(passLabel, 0, 5, 1, 1);
        gridPane.add(passField, 1, 5, 1, 1);
        gridPane.add(roleLabel, 0, 6, 1, 1);
        gridPane.add(roleCombo, 1, 6, 1, 1);
        gridPane.add(saveUserButton, 1, 7, 1, 1);

        addUser.setContent(gridPane);

        Button viewUsers = new Button("Click to view all users");
        viewUsers.setStyle("-fx-text-fill:white;");

        viewUsers.setOnAction(e -> {
            try {
                String query = "select * from users";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                rs = statement.executeQuery();
                while (rs.next()) {
                    usersData.add(new ViewUsers(
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Email"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Role")
                    ));
                    usersTable.setItems(usersData);
                }
                statement.close();
                rs.close();
                conn.close();
            } catch (Exception ex1) {
                System.err.println(ex1);
            }
        });

        //===============================table for displaying all users===================================
       
        
        TableColumn fnameColumn = new TableColumn("FIRSTNAME");
        fnameColumn.setMinWidth(150);
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));

        TableColumn lnameColumn = new TableColumn("LASTNAME");
        lnameColumn.setMinWidth(150);
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));

        TableColumn phoneColumn = new TableColumn("PHONENUMBER");
        phoneColumn.setMinWidth(100);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn emailColumn = new TableColumn("EMAIL");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn userColumn = new TableColumn("Username");
        userColumn.setMinWidth(100);
        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn passColumn = new TableColumn("Password");
        passColumn.setMinWidth(100);
        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn roleColumn = new TableColumn("Role");
        roleColumn.setMinWidth(100);
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        usersTable.getColumns().addAll(fnameColumn, lnameColumn, phoneColumn, emailColumn, userColumn, passColumn, roleColumn);

        //=================================end of users table===================================================
        VBox userBox = new VBox(8);
        userBox.getChildren().addAll(viewUsers, usersTable);
        userBox.setPadding(new Insets(10, 10, 10, 10));
        viewUser.setContent(userBox);
        userPane.getTabs().addAll(addUser, deleteUser, viewUser);
        return userPane;
    }
    //**************************************end of usersPane method**********************************************    

    public TabPane suppliersTab() {
        TabPane supplierPane = new TabPane();
        Tab addSupplier = new Tab("Add supplier");
        Tab viewSupplier = new Tab("View suppliers");

        //create table for viewing suppliers from the database
        TableView<ViewSuppliers> suppliersTable = new TableView<>();
        final ObservableList<ViewSuppliers> suppliersData = FXCollections.observableArrayList();

        Label supplierLbl = new Label("Enter Suppliers details to register");
        supplierLbl.setStyle("-fx-text-fill:white;");
        supplierNameField = new TextField();
        supplierNameField.setMaxWidth(220);
        supplierNameField.setPromptText("Supplier name");
        supplierPhoneField= new TextField();
        supplierPhoneField.setMaxWidth(220);
        supplierPhoneField.setPromptText("Phone number");
        supplierEmailField = new TextField();
        supplierEmailField.setMaxWidth(220);
        supplierEmailField.setPromptText("Email address");
        supplierAddressField = new TextField();
        supplierAddressField.setMaxWidth(220);
        supplierAddressField.setPromptText("Address");
        Button addSuppliers = new Button("save");
        addSuppliers.setMaxWidth(100);
        addSuppliers.setStyle("-fx-font-size:16");
        addSuppliers.setOnAction(e -> {
            String phone = supplierPhoneField.getText();
            if (valPhone(phone)) {
                try {
                    String query = "INSERT INTO Supplier(SupplierName,PhoneNumber,Email,Address) VALUES(?,?,?,?)";
                    conn = DbConnect.getConnection();
                    statement = conn.prepareStatement(query);
                    statement.setString(1, supplierNameField.getText());
                    statement.setString(2, phone);
                    statement.setString(3, supplierEmailField.getText());
                    statement.setString(4, supplierAddressField.getText());
                    statement.execute();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Supplier has been registered");
                    alert.showAndWait();
                    clearFields();
                } catch (Exception ex) {
                    System.err.println("supplier Error: \n" + ex.toString());
                } finally {
                    try {
                        statement.close();
                        conn.close();
                    } catch (Exception ex) {
                    }

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("invalid phone number");
                alert.showAndWait();
            }

        });

        VBox vbox1 = new VBox(10);
        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox1.getChildren().addAll(supplierLbl, supplierNameField, supplierPhoneField, supplierEmailField, supplierAddressField, addSuppliers);
        vbox1.setAlignment(Pos.CENTER);
        addSupplier.setContent(vbox1);

        Label searchLbl = new Label("Supplier name: ");
        searchLbl.setStyle("-fx-text-fill:white;");
        TextField search = new TextField();
        Button search1 = new Button("seacrh");
        search1.setStyle("-fx-text-fill:white;");
        Button viewSuppliers = new Button("Click to view all suppliers");
        viewSuppliers.setStyle("-fx-text-fill:white;");
        viewSuppliers.setOnAction(e -> {
            try {
                conn = DbConnect.getConnection();
                String query = "select *from supplier";
                statement = conn.prepareStatement(query);
                rs = statement.executeQuery();
                while (rs.next()) {
                    suppliersData.add(new ViewSuppliers(
                            rs.getInt("supplierId"),
                            rs.getString("SupplierName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Email"),
                            rs.getString("Address")
                    ));
                    suppliersTable.setItems(suppliersData);
                }
                statement.close();
                rs.close();
            } catch (Exception ex1) {
                System.err.println(ex1);
            }
        });

        GridPane searchPane = new GridPane();
        searchPane.setPadding(new Insets(10, 10, 10, 10));
        searchPane.setAlignment(Pos.CENTER);
        searchPane.setHgap(8);
        searchPane.setVgap(8);
        searchPane.add(searchLbl, 0, 0);
        searchPane.add(search, 1, 0);
        searchPane.add(search1, 2, 0);
        searchPane.add(viewSuppliers, 4, 0);

        //create column supplier name to diplay names of suppliers registered in the database
         TableColumn idColumn = new TableColumn("ID");
        idColumn.setMinWidth(150);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn snameColumn = new TableColumn("Supplier Name");
        snameColumn.setMinWidth(150);
        snameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
=======
>>>>>>> e75ec76a6173fd1eff104e7eb0c78b50284693dc

      
    public TabPane salesTab() {
        customerComboFill();
        selectProductCombo();
        TabPane salesPane = new TabPane();
        
        TableView<ViewSales> salesTable = new TableView<>();
        final ObservableList<ViewSales> salesData = FXCollections.observableArrayList();
        
         TableView<TemporaryKeeper> table = new TableView<>();
        final ObservableList<TemporaryKeeper> data = FXCollections.observableArrayList();
        Tab addSales = new Tab("Add sales");
        Tab viewSales = new Tab("View sales");
        Label addSalesLbl=new Label("Fill the fields to record sales");
        addSalesLbl.setStyle("-fx-text-fill:white;");
        
        ComboBox customerCombo = new ComboBox(list);
        customerCombo.setPromptText("Select customer ");
        customerCombo.setMaxWidth(220);
        
        ComboBox productCombo = new ComboBox(productlist);
        productCombo.setPromptText("Select product ");
        productCombo.setMaxWidth(220);
        
        TextField quantityField=new TextField();
        quantityField.setPromptText("Quantity");
        quantityField.setMaxWidth(220);
        
        Button addSale=new Button("Save");
        addSale.setStyle("-fx-font-size:16");
        addSale.setMaxWidth(100);
        addSale.setOnAction(e->{
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
            try{
            String query = "INSERT INTO Sales(CustomerId,SoldAt,UserId) VALUES(?,?,?)";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                String customerName = customerCombo.getSelectionModel().getSelectedItem().toString();
                int customerValue = list.indexOf(customerName);
                int id = new Integer(customerlistValue.get(customerValue).toString());
                statement.setInt(1, id);
                statement.setString(2, sdf.format(now));
                statement.setInt(3, this.userId);
                statement.execute();
                
                statement.close();
                conn.close();
                
                String query1 = "select max(SalesId) as SalesId from Sales";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query1);
                rs = statement.executeQuery();
                while(rs.next()){
                    latestSalesId = rs.getInt("SalesId");
                }
                rs.close();
                statement.close();
                conn.close();
                System.out.println("Latest sales id = " + latestSalesId);
                
                String query2 = "INSERT INTO Sales_Product(SalesId, ProductId, Quantity) VALUES(?,?,?)";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query2);
                //String product = productCombo.getSelectionModel().getSelectedItem().toString().trim();
               // System.out.println(productCombo.getSelectionModel().getSelectedItem().toString() + "\t" + productCombo.getSelectionModel().getSelectedIndex());
                
                int pId = new Integer(productlistValue.get(productCombo.getSelectionModel().getSelectedIndex()).toString());
                
                System.out.println(pId);
                statement.setInt(1, latestSalesId);
                statement.setInt(2,pId);
                statement.setString(3, quantityField.getText());
                statement.execute();
                
                quantityField.clear();
               
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("sales recorded successfully");
                alert.showAndWait();
                
                statement.close();
                conn.close();
            }
                 catch (Exception ex) {
                     ex.printStackTrace();
                    //System.err.println("sales Error: \n" + ex.toString());
                } /*finally {
                    try {
                        statement.close();
                        conn.close();
                    } catch (Exception ex) {
                    }

                }*/
            
   
            //
        });
        //layout for add sales button,comboboxes and textfield
        VBox salesBox = new VBox(8);
        salesBox.setPadding(new Insets(10, 10, 10, 10));
        salesBox.getChildren().addAll(addSalesLbl,customerCombo,productCombo,quantityField,addSale);
        //salesBox.setAlignment(Pos.CENTER);
        
        Label label=new Label("Selected items and quantity");
        label.setStyle("-fx-text-fill:white");
        //column for productname
        TableColumn<TemporaryKeeper,String> pnameColumn = new TableColumn<>("PRODUCT NAME");
        pnameColumn.setMinWidth(200);
        pnameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        //column for quantity
        TableColumn<TemporaryKeeper,Integer> quantColumn = new TableColumn<>("QUANTITY");
        quantColumn.setMinWidth(200);
        quantColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        table.getColumns().addAll( pnameColumn, quantColumn);
        table.setItems(data);
        
        //layout for table alone
        VBox tableLayout=new VBox();
        tableLayout.setPadding(new Insets(10, 10, 10, 10));
        tableLayout.getChildren().addAll(label,table);
        //layout for the whole form contents
        HBox hbox =new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(salesBox,tableLayout);
        
        addSales.setContent(hbox);
        
        Label searchLbl = new Label("Customer Name");
        searchLbl.setStyle("-fx-text-fill:white;");
        ComboBox comboBox = new ComboBox(list);
        comboBox.setPromptText("Select customer");
        comboBox.setEditable(true);
        Button searchSalesButton=new Button("Search");
        searchSalesButton.setOnAction(e->{
            salesData.clear();
            try{
        String salesCombo=comboBox.getSelectionModel().getSelectedItem().toString();
        String sql="SELECT product.productName,product.productDescription,product.sellingPrice, sales_product.quantity, sales.soldAt, customer.firstName,customer.LASTNAME"
                        + " FROM product INNER JOIN sales_product ON product.productId = sales_product.productId "
                        + "INNER JOIN sales ON sales_product.salesId = sales.salesId "
                        + "INNER JOIN customer ON sales.customerId = customer.customerId WHERE CONCAT(customer.FirstName,' ',customer.LastName)= '"+salesCombo+"'";
        conn=DbConnect.getConnection();
        statement = conn.prepareStatement(sql);
        rs=statement.executeQuery();
        while(rs.next()){
            salesData.add(new ViewSales(
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("productName"),
                            rs.getString("productDescription"),
                            rs.getInt("sellingPrice"),
                            rs.getInt("quantity"),
                            rs.getString("soldAt")
                    ));
                    salesTable.setItems(salesData);
                    
        }
        
        
        statement.close();
        rs.close();
        }
            catch(SQLException ex3){
            System.err.println("sales ERROR:"+ex3);
        }
        });
      
       
        Button viewSalesButton = new Button("view sales records");
        
        GridPane searchPane = new GridPane();
        searchPane.setPadding(new Insets(10, 10, 10, 10));
        searchPane.setAlignment(Pos.CENTER);
        searchPane.setHgap(8);
        searchPane.setVgap(8);

        searchPane.add(searchLbl, 0, 0);
        searchPane.add(comboBox, 1, 0);
        searchPane.add(searchSalesButton, 2, 0);
        searchPane.add(viewSalesButton, 3, 0);
        
        //=====================================sales table================================================
        
        TableColumn<ViewSales,String> fnameColumn = new TableColumn<>("FIRSTNAME");
        fnameColumn.setMinWidth(120);
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));

        //set column for product prices
        TableColumn<ViewSales,String> lnameColumn = new TableColumn<>("LASTNAME");
        lnameColumn.setMinWidth(120);
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //set column for product quantity
        TableColumn<ViewSales,String> productColumn = new TableColumn<>("PRODUCT NAME ");
        productColumn.setMinWidth(150);
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<ViewSales,String> descrColumn = new TableColumn<>("DESCRIPTION");
        descrColumn.setMinWidth(150);
        descrColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        TableColumn<ViewSales,Integer> priceColumn = new TableColumn<>("PRICE");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        TableColumn<ViewSales,Integer> quanColumn = new TableColumn<>("QUANTITY");
        quanColumn.setMinWidth(100);
        quanColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        TableColumn<ViewSales,String> timeColumn = new TableColumn<>("DATE");
        timeColumn.setMinWidth(150);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        
        //TableColumn<ViewSales,Integer> salesColumn = new TableColumn<>("SALES");
       // salesColumn.setMinWidth(150);
        //salesColumn.setCellValueFactory(new PropertyValueFactory<>("sales"));

        //add all columns to the table
        salesTable.getColumns().addAll(fnameColumn, lnameColumn, productColumn, descrColumn,priceColumn,quanColumn,timeColumn);
        salesTable.setItems(salesData);
        
        viewSalesButton.setOnAction(e->{
            salesData.clear();
            try {
                String query="SELECT product.productName,product.productDescription,product.sellingPrice, sales_product.quantity, sales.soldAt, customer.firstName,customer.LASTNAME"
                        + " FROM product INNER JOIN sales_product ON product.productId = sales_product.productId "
                        + "INNER JOIN sales ON sales_product.salesId = sales.salesId "
                        + "INNER JOIN customer ON sales.customerId = customer.customerId";

                conn=DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                rs = statement.executeQuery();
                while(rs.next()){
                     salesData.add(new ViewSales(
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("productName"),
                            rs.getString("productDescription"),
                            rs.getInt("sellingPrice"),
                            rs.getInt("quantity"),
                            rs.getString("soldAt")
                    ));
                    salesTable.setItems(salesData);
                    
                }
                statement.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        salesTable.refresh();
        
        VBox salesLayout=new VBox(8);
        salesLayout.setPadding(new Insets(10, 10, 10, 10));
        salesLayout.getChildren().addAll(searchPane,salesTable);
        viewSales.setContent(salesLayout);
        salesPane.getTabs().addAll(addSales,viewSales);
        return salesPane;
    }
//******************************************************************************************
 
   
    public void productComboFill() {
        try {
            conn = DbConnect.getConnection();
            String query = "select SupplierName,SupplierId from Supplier ";
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("SupplierName"));
                optionValue.add(rs.getString("SupplierId"));
            }
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public void customerComboFill(){
        try {
            conn=DbConnect.getConnection();
            String query= "select CustomerId, firstName, LastName from Customer";
            statement=conn.prepareStatement(query);
            rs=statement.executeQuery();
            while(rs.next()){
                list.add(rs.getString("firstName")+ " " +rs.getString("LastName"));
                customerlistValue.add(rs.getString("CustomerId"));
            }
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
           
        public void selectProductCombo(){
        try {
            conn=DbConnect.getConnection();
            String query= "select ProductId, ProductName as P from Product";
            statement=conn.prepareStatement(query);
            rs=statement.executeQuery();
            while(rs.next()){
               productlist.add(rs.getString("P"));
               productlistValue.add(rs.getString("ProductId"));
            }
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public void clearFields(){
                    
        }

}
