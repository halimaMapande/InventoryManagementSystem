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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class Products  {
    
     TextField nameField,descriptionField,buyingPriceField,sellingPriceField;
     PreparedStatement pst=null;
     ResultSet rs=null;
     Connection conn=null;
     TableView<ViewProducts> productTable = new TableView<>();
     final ObservableList<ViewProducts> productData = FXCollections.observableArrayList();
     final ObservableList options = FXCollections.observableArrayList();
     final ObservableList optionValue = FXCollections.observableArrayList();
     int id;
     Button deleteButton=new Button("delete");
     
     public TabPane productTab() {
        conn = DbConnect.getConnection();
        viewProducts();
        productComboFill();
        deleteButton.setDisable(true);
        
        productTable.setOnMouseClicked(e->{
        deleteButton.setDisable(false);   
        productTable.getSelectionModel().getSelectedItem();
        id=productTable.getSelectionModel().getSelectedItem().getProductId();
        });
       
        TabPane productPane = new TabPane();
        Tab addTab = new Tab("Add product");
        Tab viewTab = new Tab("View Products");

        Label productLbl = new Label("Enter product details to register");
        productLbl.setStyle("-fx-text-fill:white;");
        ComboBox supplierCombo = new ComboBox(options);

        supplierCombo.setPromptText("Select supplier ");
        supplierCombo.setMaxWidth(220);

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
        Button addProduct = new Button("Save");
        addProduct.setMaxWidth(100);
        addProduct.setStyle("-fx-font-size:16");
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
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("Product is successfulregistered");
                alert.showAndWait();
                
            } 
            catch (Exception ex) {
                System.err.println("product Error: \n" + ex.toString());
            } finally {
                try {
                    pst.close();
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

        TableColumn<ViewProducts,Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));       
        
        TableColumn<ViewProducts,String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        //set column for product prices
        TableColumn<ViewProducts,String> descriptionColumn = new TableColumn<>("Product Description");
        descriptionColumn.setMinWidth(200);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("productDescription"));

        TableColumn<ViewProducts,Integer> buyColumn = new TableColumn("Buying Price");
        buyColumn.setMinWidth(100);
        buyColumn.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));

        TableColumn<ViewProducts,Integer> saleColumn = new TableColumn<>("Selling Price");
        saleColumn.setMinWidth(100);
        saleColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        productTable.getColumns().addAll(nameColumn, descriptionColumn, buyColumn, saleColumn);
        
        
        deleteButton.setStyle("-fx-text-fill:white;");
        deleteButton.setOnAction(e->{
           Alert confAlert = new Alert(Alert.AlertType.CONFIRMATION);
           confAlert.setTitle("Confirmation dialog");
           confAlert.setHeaderText(null);
           confAlert.setContentText("Are sure you want to deleted the item?");
            
           Optional<ButtonType> action=confAlert.showAndWait();
           if(action.get()==ButtonType.OK){
                
            
            try {
                String query="DELETE FROM product where ProductId=?";
                pst = conn.prepareStatement(query);
                pst.setInt(1, id);
                pst.execute();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("Product is successful deleted");
                alert.showAndWait();
                deleteButton.setDisable(true);
                productTable.refresh();
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

       
        viewPane.add(deleteButton, 0, 0);

        VBox centerMenu = new VBox(8);
        centerMenu.setPadding(new Insets(10, 10, 10, 10));
        centerMenu.getChildren().addAll(viewPane, productTable);
        viewTab.setContent(centerMenu);

        productPane.getTabs().addAll(addTab, viewTab);

        return productPane;
    }
     
      public void productComboFill() {
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
            try {
                String query = "SELECT ProductId,ProductName,ProductDescription,BuyingPrice,SellingPrice FROM product";
                
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
                while (rs.next()) {
                    productData.add(new ViewProducts(
                            rs.getInt("ProductId"),
                            rs.getString("ProductName"),
                            rs.getString("ProductDescription"),
                            rs.getInt("BuyingPrice"),
                            rs.getInt("SellingPrice")
                    ));
                    productTable.setItems(productData);
                    productTable.refresh();
                }
                pst.close();
                rs.close();
                
            } 
            catch (Exception ex1) {
                System.err.println(ex1);
            }
        
      }
      
      public void clearFields(){
           nameField.clear();
           descriptionField.clear();
           buyingPriceField.clear();
           sellingPriceField.clear();
      }
}
