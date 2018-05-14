/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class Products  {
    
     TextField nameField,descriptionField,buyingPriceField,sellingPriceField;
     TextField updateBuyingPrice,updateDescription,updateSellingPrice,updateProductName;
     PreparedStatement pst=null;
       ComboBox updateSupplier;
     ResultSet rs=null;
     Connection conn=null;
     TableView<ViewProducts> productTable = new TableView<>();
     final ObservableList<ViewProducts> productData = FXCollections.observableArrayList();
     final ObservableList options = FXCollections.observableArrayList();
     final ObservableList optionValue = FXCollections.observableArrayList();
     int id;
     Button deleteProduct=new Button("Delete ");
     Button updateProduct=new Button("Update ");
     
     public TabPane productTab() {
         DropShadow shadow=new DropShadow();
        conn = DbConnect.getConnection();//establish connection with mysql db
        viewProducts();
        productComboFill();
        
        deleteProduct.setDisable(true);
        deleteProduct.setMaxWidth(220);
        deleteProduct.setStyle("-fx-font-size:16");
        
        //this code set a table event that enable user to manipulate the selected row (delete,update)
        productTable.setOnMouseClicked(e->{
        deleteProduct.setDisable(false);   
        productTable.getSelectionModel().getSelectedItem();
        id=productTable.getSelectionModel().getSelectedItem().getProductId();
        });
        
        updateProduct.setDisable(true);
        updateProduct.setMaxWidth(220);
        updateProduct.setStyle("-fx-font-size:16");
        updateProduct.setOnAction(e->{
            try{
              String query="UPDATE product set productName=?,productDescription=?,buyingPrice=?,sellingPrice=? WHERE ProductId=?";
                conn=DbConnect.getConnection();
                pst=conn.prepareStatement(query);
                pst.setString(1, updateProductName.getText());
                pst.setString(2, updateDescription.getText());
                pst.setString(3, updateBuyingPrice.getText());
                pst.setString(4, updateSellingPrice.getText());
                pst.setInt(5,id );
                if( pst.executeUpdate()==1){
                Alert updateAlert=new Alert(Alert.AlertType.INFORMATION);
               updateAlert.setTitle("Information dialog");
               updateAlert.setHeaderText(null);
               updateAlert.setContentText("product information have been updated");
               updateAlert.showAndWait();
                deleteProduct.setDisable(true);
                //cleardata
                clearFields();
                //update button unclicked
                updateProduct.setDisable(true);
               pst.close();
               viewProducts();
                }
               
            } catch (SQLException ex) {
                Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        );
        
       
        TabPane productPane = new TabPane();
        Tab addTab = new Tab("Add product");
        Tab viewTab = new Tab("View Products");

        Label productLbl = new Label("Enter product details to register");
        ComboBox supplierCombo = new ComboBox(options);

        supplierCombo.setPromptText("Select supplier ");
        supplierCombo.setMaxWidth(220);

        nameField = new TextField();
        nameField.setPromptText("PPRODUCT NAME");
        nameField.setMaxWidth(220);
        descriptionField = new TextField();
        descriptionField.setPromptText("DESCRIPTION");
        descriptionField.setMaxWidth(220);
        buyingPriceField = new TextField();
        buyingPriceField.setPromptText("BUYING PRICE");
        buyingPriceField.setMaxWidth(220);
        sellingPriceField = new TextField();
        sellingPriceField.setPromptText("SELLING PRICE");
        sellingPriceField.setMaxWidth(220);
        Button addProduct = new Button("Save");
        addProduct.setMaxWidth(220);
        addProduct.setStyle("-fx-font-size:16");
        addProduct.setMaxWidth(220);
        
        addProduct.setOnMouseEntered(e->{
            addProduct.setEffect(shadow);
        });
        addProduct.setOnMouseExited(e->{
            addProduct.setEffect(null);
        });
        
        
        addProduct.setOnAction(e -> {
            
            try {
                String query = "INSERT INTO product(productName,productDescription,buyingPrice,sellingPrice,supplierId) VALUES(?,?,?,?,?)";
                
                pst = conn.prepareStatement(query);
                String supplierName = supplierCombo.getSelectionModel().getSelectedItem().toString();
                int supplierValue = options.indexOf(supplierName);
                int id = new Integer(optionValue.get(supplierValue).toString());
                pst.setString(1, nameField.getText());
                pst.setString(2, descriptionField.getText());
                pst.setString(3, buyingPriceField.getText());
                pst.setString(4, sellingPriceField.getText());
                pst.setInt(5, id);
                pst.execute();
                
                clearFields();
                supplierCombo.getSelectionModel().clearSelection();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("Product is successful registered");
                alert.showAndWait();
                
            } 
            catch (Exception ex) {
                System.err.println("product Error: \n" + ex.toString());
            } finally {
                try {
                    pst.close();
                   // conn.close();
                } catch (Exception ex) {
                }

            }
            viewProducts();
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(productLbl, supplierCombo, nameField, descriptionField, buyingPriceField, sellingPriceField, addProduct);
        vbox.setAlignment(Pos.CENTER);
        addTab.setContent(vbox);
        //Creating  columns for the table that will be used to display products info in addTab tab
        TableColumn<ViewProducts,Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));       
        
        TableColumn<ViewProducts,String> nameColumn = new TableColumn<>("PRODUCT NAME");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<ViewProducts,String> descriptionColumn = new TableColumn<>("PRODUCT DESCRIPTION");
        descriptionColumn.setMinWidth(200);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("productDescription"));

        TableColumn<ViewProducts,Integer> buyColumn = new TableColumn("BUYING PRICE");
        buyColumn.setMinWidth(150);
        buyColumn.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));

        TableColumn<ViewProducts,Integer> saleColumn = new TableColumn<>("SELLING PRICE");
        saleColumn.setMinWidth(150);
        saleColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        
        

        productTable.getColumns().addAll(nameColumn, descriptionColumn, buyColumn, saleColumn);
       
        //An alert that ask user to confirm if he/she is sure of deleting a selected item after clicking delete button
        deleteProduct.setOnAction(e->{
           Alert confAlert = new Alert(Alert.AlertType.CONFIRMATION);
           confAlert.setTitle("Confirmation dialog");
           confAlert.setHeaderText(null);
           confAlert.setContentText("Are sure you want to delete the item?");
            
           Optional<ButtonType> action=confAlert.showAndWait();
           if(action.get()==ButtonType.OK){
                
            //query to delete selected item when delete button is clicked
            try {
                String query="DELETE FROM product where ProductId=?";
                pst = conn.prepareStatement(query);
                pst.setInt(1, id);
                if(pst.executeUpdate()==1){
              //An alert that gives info to user if the item is deleted from the db  
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("Product is successful deleted");
                alert.showAndWait();
                //this line make a button unclickable
                deleteProduct.setDisable(true);
                //cleardata
                clearFields();
                //disable update
                updateProduct.setDisable(true);
                //refresh table after deleting  item(s)
               viewProducts();
                }
            } 
            catch (SQLException ex) {
                Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        });

        GridPane viewPane = new GridPane();
        viewPane.setPadding(new Insets(10, 10, 10, 10));
        viewPane.setHgap(10);
        viewPane.setVgap(10);

       
        viewPane.add(deleteProduct, 0, 0);
        viewPane.add(updateProduct, 1, 0);
        
        //textfields for product updation
        updateProductName=new TextField();
        updateProductName.setMaxWidth(220);
        updateProductName.setPromptText("Product name");
        
        updateDescription=new TextField();
        updateDescription.setMaxWidth(220);
        updateDescription.setPromptText("Product description");
        
        updateBuyingPrice=new TextField();
        updateBuyingPrice.setMaxWidth(220);
        updateBuyingPrice.setPromptText("Buying price");
        
        updateSellingPrice=new TextField();
        updateSellingPrice.setMaxWidth(220);
        updateSellingPrice.setPromptText("Selling price");
       
        updateSupplier=new ComboBox(options);
        updateSupplier.setMaxWidth(220);
        updateSupplier.setPromptText("Supplier name");
        
        GridPane updateGrid=new GridPane();
        updateGrid.setPadding(new Insets(10, 10, 10, 10));
        updateGrid.setHgap(10);
        updateGrid.setVgap(10);
        
        updateGrid.add(updateProductName,0,0);
        updateGrid.add(updateDescription,1,0);
        updateGrid.add(updateBuyingPrice,0,1);
        updateGrid.add(updateSellingPrice,1,1);
        updateGrid.add(updateSupplier,0,2);
        
        updateProduct.setDisable(true);
        productTable.setOnMouseClicked(e->{
         deleteProduct.setDisable(false);
         updateProduct.setDisable(false);
               id=productTable.getSelectionModel().getSelectedItem().getProductId();
               updateProductName.setText(productTable.getSelectionModel().getSelectedItem().getProductName());
               updateDescription.setText(productTable.getSelectionModel().getSelectedItem().getProductDescription());
             updateBuyingPrice.setText(productTable.getSelectionModel().getSelectedItem().getBuyingPrice());
              updateSellingPrice.setText(productTable.getSelectionModel().getSelectedItem().getSellingPrice());
                     
        });
        
        
        VBox centerMenu = new VBox(8);
        centerMenu.setPadding(new Insets(10, 10, 10, 10));
        centerMenu.getChildren().addAll(viewPane, productTable,updateGrid);
        viewTab.setContent(centerMenu);

        productPane.getTabs().addAll(addTab, viewTab);

        return productPane;
    }
     
      public void productComboFill() {
          options.clear();
        try {
            conn = DbConnect.getConnection();
            String query = "select SupplierName,SupplierId from Supplier ";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("SupplierName"));
                optionValue.add(rs.getString("SupplierId"));
            }
            pst.close();
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      public void viewProducts() {
          productData.clear();
            try {
                String query = "SELECT * FROM product";
                
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
                while (rs.next()) {
                    productData.add(new ViewProducts(
                            rs.getInt("ProductId"),
                            rs.getString("ProductName"),
                            rs.getString("ProductDescription"),
                            rs.getString("BuyingPrice"),
                            rs.getString("SellingPrice")
                           
                    ));
                    productTable.setItems(productData);
                    //productTable.refresh();
                }
                pst.close();
                rs.close();
                
            } 
            catch (Exception ex1) {
                System.err.println(ex1);
            }
         productTable.refresh();
      }
      //this method clear fields after insertion is successful
      public void clearFields(){
           nameField.clear();
           descriptionField.clear();
           buyingPriceField.clear();
           sellingPriceField.clear();
      }
}
