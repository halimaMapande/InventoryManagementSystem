package wholesaleinventorysystem;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    Label msglabel;
    Label validatemsg;
    private int pId;
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
    ComboBox productCombo;
    TextField quantityField,totalAmount;
     ComboBox customerCombo;
     int checkquant;
      //track if the product is in the table of shopcat
    int userId;
    int latestSalesId;
    int sumtotal=0;
    public TabsClass(int userId) {
        this.userId = userId;
    }

      
    public TabPane salesTab() {
       
        customerComboFill();
        selectProductCombo();
        TabPane salesPane = new TabPane();
        
        TableView<ViewSales> salesTable = new TableView<>();
        final ObservableList<ViewSales> salesData = FXCollections.observableArrayList();
        salesTable.getItems().clear();

         TableView<TemporaryKeeper> table = new TableView<>();
        final ObservableList<TemporaryKeeper> data = FXCollections.observableArrayList();

        Tab addSales = new Tab("Add sales");
        Tab viewSales = new Tab("View sales");
        Label addSalesLbl=new Label("Fill the fields to record sales");
        
        customerCombo = new ComboBox(list);
        customerCombo.setPromptText("Select customer ");
        customerCombo.setMaxWidth(220);
        
        productCombo = new ComboBox(productlist);
        productCombo.setPromptText("Select product ");
        productCombo.setMaxWidth(220);
        //check th quantity of the product
        productCombo.valueProperty().addListener(e -> {
            validatemsg.setText(null);
            int track=0;
            if(table.getItems().isEmpty()){
                checkquant=checkQuantity(productCombo.getValue().toString());
            }
            
            //check if the product alraedy in table
            else{
            for (TemporaryKeeper data1 : table.getItems()) {
                if(data1.getProductName().equals(productCombo.getValue().toString())){
                    track=1;
                    checkquant=checkQuantity(productCombo.getValue().toString())-data1.getQuantity();
                }
                
                        
            } 
            if(track==0){
                    checkquant=checkQuantity(productCombo.getValue().toString()); 
                }
            }
            msglabel.setText("The available quantity in stock is " +checkquant );
                 msglabel.setStyle("-fx-text-fill:red");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        quantityField=new TextField();
        quantityField.setPromptText("Quantity");
        quantityField.setMaxWidth(220);
         
         
       
        Button sendButton=new Button("Send");
        sendButton.setStyle("-fx-font-size:16");
        sendButton.setMaxWidth(100);
         sendButton.setOnAction( e->{
             //check the quantity exceeds the quantity from stock
             if (Integer.parseInt(quantityField.getText())>checkquant) {
                 validatemsg.setText("The quantity exceeed quantity in stock" );
                 validatemsg.setStyle("-fx-text-fill:red");
            }
            else{
                 validatemsg.setText(null);
            
           String prod = productCombo.getSelectionModel().getSelectedItem().toString();
           int qunat=Integer.parseInt(quantityField.getText());
//            int sumtotal;
            //sumtotal = Integer.parseInt(totalAmount.getText());
           //add product name and quantity to the table before save them in database 
            data.add(new TemporaryKeeper(prod,qunat));
            table.setItems(data);
            //set new checkpoint
            checkquant=checkquant-Integer.parseInt(quantityField.getText());
            msglabel.setText("The available quantity in stock is " +checkquant );
                 msglabel.setStyle("-fx-text-fill:red");
           //catData.add(new SalesCatalog(prod,qunat,sumtotal));
            // catalogTable.setItems(catData);
             }
        });
        //complete send button
       
        Button addSale=new Button("Save");
        addSale.setStyle("-fx-font-size:16");
        addSale.setMaxWidth(100);
        addSale.setOnAction(e->{
//            //check the quantity exceeds the quantity from stock
           
             for (TemporaryKeeper newdata1 : table.getItems()) {
                 //when you click save button calculate the total sale first
                 System.out.println(newdata1.getProductName());
                   System.out.println(getPrice(newdata1.getProductName()));
                 sumtotal=sumtotal+getPrice(newdata1.getProductName());
                 
             }
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
            try{
            String query = "INSERT INTO Sales(CustomerId,SoldAt,UserId,TotalCost) VALUES(?,?,?,?)";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                String customerName = customerCombo.getSelectionModel().getSelectedItem().toString();
                int customerValue = list.indexOf(customerName);
                int id = new Integer(customerlistValue.get(customerValue).toString());
                statement.setInt(1, id);
                statement.setString(2, sdf.format(now));
                statement.setInt(3, this.userId);
                statement.setInt(4, sumtotal);
                int result=statement.executeUpdate();
                if(result==1){
                System.out.println(id + " " +this.userId + " "+ sumtotal);
                sumtotal=0;
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("sales recorded successfully");
                alert.showAndWait();
                }
                
                statement.close();
                conn.close();
                customerCombo.getSelectionModel().clearSelection();
                
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
               
                 
              //loop through table adn take item from cat and add to the table
                
                for (TemporaryKeeper newdata1 : table.getItems()) {
                    //System.out.println(String.format("%s", newdata1.getProductName()));
              //get product id      
                     System.out.println("Latest sales id = " + latestSalesId);
                     String prd=newdata1.getProductName();
                     int qty=newdata1.getQuantity();
                //System.out.println(newdata1.productId(prd));
               
                String query2 = "INSERT INTO Sales_Product(SalesId, ProductId, Quantity) VALUES(?,?,?)";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query2);
                //String product = productCombo.getSelectionModel().getSelectedItem().toString().trim();
               // System.out.println(productCombo.getSelectionModel().getSelectedItem().toString() + "\t" + productCombo.getSelectionModel().getSelectedIndex());
               pId = newdata1.productId(prd);
                  
                //int pId = new Integer(productlistValue.get(productCombo.getSelectionModel().getSelectedIndex()).toString());
                
                System.out.println(pId);
                statement.setInt(1, latestSalesId);
                statement.setInt(2,pId);
                statement.setInt(3,qty );
                int prove=statement.executeUpdate();
                 
                if(prove==1){
                    
                    
                     int qt=newdata1.getQuantity();
                    String quer = "UPDATE stock set quantity=quantity-? where productId=?";
                  PreparedStatement pst=null;
                pst = conn.prepareStatement(quer);
                //String productName = customerCombo.getSelectionModel().getSelectedItem().toString();
                //int productValue = productlist.indexOf(productName);
                //int pid = new Integer(productlistValue.get(productValue).toString());
                
                pst.setInt(1, qt);
                 pst.setInt(2, pId);
                 int pr=pst.executeUpdate();
                    if (pr==1) {
                         Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("updated");
                alert.showAndWait();
                    }

            
                }
                
                quantityField.clear();
                }
               
               
                data.clear();
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
        validatemsg=new Label();
        msglabel =new Label();
        VBox salesBox = new VBox(8);
        salesBox.setPadding(new Insets(10, 10, 10, 10));
        salesBox.getChildren().addAll(validatemsg,addSalesLbl,customerCombo,productCombo,quantityField,msglabel,sendButton);
        //salesBox.setAlignment(Pos.CENTER);
        
        Label label=new Label("Selected items and quantity");
       
        TableView<SalesCatalog> catalogTable=new TableView<>();
        final ObservableList<SalesCatalog> catData=FXCollections.observableArrayList();
        
        
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
        table.refresh();
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
      
        ComboBox comboBox = new ComboBox(list);
        comboBox.setPromptText("Select customer");
        comboBox.setEditable(true);
        Button searchSalesButton=new Button("Search");
        searchSalesButton.setOnAction(e->{
            salesData.clear();
            //salesTable.getSelectionModel().clearSelection();
            try{
        String salesCombo=comboBox.getSelectionModel().getSelectedItem().toString();
        String sql="SELECT product.productName,product.productDescription,product.sellingPrice, "
                        + " sales.soldAt,quantity*sellingPrice as 'TotalCost',"
                         + " sales.soldAt,sales.TotalCost, customer.firstName,customer.LASTNAME"
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
                            rs.getString("soldAt"),
                            rs.getInt("TotalCost")
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
        
        DatePicker date=new DatePicker();
        date.setPromptText("Sales date");
        date.setMaxWidth(220);
        
        Button searchDate=new Button("Search");
        searchDate.setMaxWidth(220);
        //filter the sales record acording to date
        searchDate.setOnAction(e->{
            salesData.clear();
            //pick the date 
            java.sql.Date  salesdate=  java.sql.Date.valueOf(date.getValue());
            try {
                String query="SELECT product.productName,product.productDescription,"
                        + "product.sellingPrice, sales_product.quantity,"
                        + " sales.soldAt,quantity*sellingPrice as 'TotalCost'"
                        + ", customer.firstName,customer.LASTNAME"
                        + " FROM product INNER JOIN sales_product ON product.productId = sales_product.productId "
                        + "INNER JOIN sales ON sales_product.salesId = sales.salesId "
                        + "INNER JOIN customer ON sales.customerId = customer.customerId where soldAt=?";

                conn=DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                statement.setDate(1, salesdate);
                rs = statement.executeQuery();
                
                System.out.println(salesdate);
                while(rs.next()){
                     salesData.add(new ViewSales(
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("productName"),
                            rs.getString("productDescription"),
                            rs.getInt("sellingPrice"),
                            rs.getInt("quantity"),
                            rs.getString("soldAt"),
                            rs.getInt("TotalCost")
                    ));
                    salesTable.setItems(salesData);
                    
                }
                statement.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        GridPane searchPane = new GridPane();
        searchPane.setPadding(new Insets(10, 10, 10, 10));
        searchPane.setAlignment(Pos.CENTER);
        searchPane.setHgap(8);
        searchPane.setVgap(8);

        searchPane.add(viewSalesButton, 0, 0);
        searchPane.add(comboBox, 1, 0);
        searchPane.add(searchSalesButton, 2, 0);
        searchPane.add(date,3,0);
        searchPane.add(searchDate,4,0);
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
        
        TableColumn<ViewSales,Integer> salesColumn = new TableColumn<>("SALES AMOUNT");
        salesColumn.setMinWidth(150);
        salesColumn.setCellValueFactory(new PropertyValueFactory<>("sales"));
        
       

        //add all columns to the table
        salesTable.getColumns().addAll(fnameColumn, lnameColumn, productColumn, descrColumn,priceColumn,quanColumn,timeColumn,salesColumn);
        salesTable.setItems(salesData);
        
        viewSalesButton.setOnAction(e->{
            salesData.clear();
            try {
                String query="SELECT product.productName,product.productDescription,product.sellingPrice, sales_product.quantity, sales.soldAt,sales.TotalCost, customer.firstName,customer.LASTNAME"
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
                            rs.getString("soldAt"),
                            rs.getInt("TotalCost")
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
            list.clear();
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
            productlist.clear();
        try {
            conn=DbConnect.getConnection();
            String query= "select product.ProductId, ProductName as P from Product join stock on "
                    + "product.ProductId=stock.productId where quantity>0";
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
        //function return quantity so as to prove the quantity doesnot exist the quantity in stock
        public int checkQuantity(String product){
            try{
                
            conn = DbConnect.getConnection();
            String sql ="SELECT quantity FROM stock join product "
                    + "on product.ProductId=stock.productId WHERE ProductName =?";
            statement = conn.prepareStatement(sql);
            statement.setString(1,product);
            rs = statement.executeQuery();
              while(rs.next()){
                  
                  int qty = rs.getInt("quantity");
                 
                  return qty;
                  //sumtotal = Integer.toString(sum);
                  //totalAmount.setText(sumtotal);
              }
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }  
        
        public int getPrice(String product){
        try {
           // String productName = productCombo.getSelectionModel().getSelectedItem().toString();
            String quantity = quantityField.getText();
            conn = DbConnect.getConnection();
            String sql ="SELECT * FROM product WHERE ProductName =?";
            statement = conn.prepareStatement(sql);
            statement.setString(1,product);
            rs = statement.executeQuery();
              while(rs.next()){
                  String price = rs.getString("SellingPrice");
                  int pricep = Integer.parseInt(price);
                  int qty = Integer.parseInt(quantity);
                  int sum = pricep*qty;
                  return sum;
                  //sumtotal = Integer.toString(sum);
                  //totalAmount.setText(sumtotal);
              }
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }

}