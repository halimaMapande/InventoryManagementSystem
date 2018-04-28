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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.VBox;


/**
 *
 * @author josephine
 */
public class Products  {
    
     TextField nameField,descriptionField,buyingPriceField,sellingPriceField;
     PreparedStatement pst=null;
     ResultSet rs=null;
     Connection conn=null;
     TableView<ViewProducts> productTable;
     final ObservableList options = FXCollections.observableArrayList();
     final ObservableList optionValue = FXCollections.observableArrayList();
     
     
     public TabPane productTab() {
        conn = DbConnect.getConnection();
        productComboFill();
        TabPane productPane = new TabPane();
        
        
        

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

        productTable = new TableView<>();
        final ObservableList<ViewProducts> productData = FXCollections.observableArrayList();
        
        TableColumn nameColumn = new TableColumn("Product Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        //set column for product prices
        TableColumn descriptionColumn = new TableColumn("Product Description");
        descriptionColumn.setMinWidth(200);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("productDescription"));

        TableColumn buyColumn = new TableColumn("Buying Price");
        buyColumn.setMinWidth(100);
        buyColumn.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));

        TableColumn saleColumn = new TableColumn("Selling Price");
        saleColumn.setMinWidth(100);
        saleColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        productTable.getColumns().addAll(nameColumn, descriptionColumn, buyColumn, saleColumn);
        
        
        Button viewButton = new Button("click to view products");
        viewButton.setStyle("-fx-text-fill:white;");

         viewButton.setOnAction(e -> {
            try {
                String query = "SELECT ProductName,ProductDescription,BuyingPrice,SellingPrice FROM product";
                
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
                while (rs.next()) {
                    productData.add(new ViewProducts(
                            rs.getString("ProductName"),
                            rs.getString("ProductDescription"),
                            rs.getInt("BuyingPrice"),
                            rs.getInt("SellingPrice")
                    ));
                    productTable.setItems(productData);
                }
                pst.close();
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
      
      public void clearFields(){
           nameField.clear();
           descriptionField.clear();
           buyingPriceField.clear();
           sellingPriceField.clear();
      }
}
