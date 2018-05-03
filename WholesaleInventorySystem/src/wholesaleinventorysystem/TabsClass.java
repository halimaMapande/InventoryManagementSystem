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
import javafx.event.ActionEvent;
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

      
    public TabPane salesTab() {
        customerComboFill();
        selectProductCombo();
        TabPane salesPane = new TabPane();
        
        TableView<ViewSales> salesTable = new TableView<>();
        final ObservableList<ViewSales> salesData = FXCollections.observableArrayList();
        
         TableView<Item> table = new TableView<>();
        final ObservableList<Item> data = FXCollections.observableArrayList();

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
        
        Button sendButton=new Button("Send");
        sendButton.setStyle("-fx-font-size:16");
        sendButton.setMaxWidth(100);
        sendButton.setOnAction( e->{
            String prod = productCombo.getSelectionModel().getSelectedItem().toString();
            String qunat=quantityField.getText();
             table.setItems(data);
            data.add(new Item(prod,qunat));
          
          
        });
       //column for productname
        TableColumn<Item,String> pnameColumn = new TableColumn<>("PRODUCT NAME");
        pnameColumn.setMinWidth(200);
        pnameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        //column for quantity
        TableColumn<Item,String> quantColumn = new TableColumn<>("QUANTITY");
        quantColumn.setMinWidth(200);
        quantColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        table.getColumns().addAll(pnameColumn, quantColumn);
        //table.setItems(data);
        
        //layout for table alone
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
        salesBox.getChildren().addAll(addSalesLbl,customerCombo,productCombo,quantityField,sendButton);
        //salesBox.setAlignment(Pos.CENTER);
        
        Label label=new Label("Selected items and quantity");
        label.setStyle("-fx-text-fill:white");
        
        VBox tableLayout=new VBox();
        tableLayout.setPadding(new Insets(10, 10, 10, 10));
        tableLayout.getChildren().addAll(label,table);
        //layout for the whole form contents
        HBox hbox =new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(salesBox,tableLayout);
        
        VBox totalLayout=new VBox(8);
        totalLayout.setPadding(new Insets(10, 10, 10, 10));
        totalLayout.setAlignment(Pos.CENTER);
        totalLayout.getChildren().addAll(hbox,addSale);
        
        addSales.setContent(totalLayout);
        
       
        
        
        
        
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