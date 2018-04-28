/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class Suppliers {
    TextField supplierNameField,supplierPhoneField,supplierEmailField,supplierAddressField;
    PreparedStatement pst=null;
    ResultSet rs=null;
    Connection conn=null;
     TableView<ViewSuppliers> suppliersTable;
   public TabPane suppliersTab() {
       conn = DbConnect.getConnection();
        TabPane supplierPane = new TabPane();
        Tab addSupplier = new Tab("Add supplier");
        Tab viewSupplier = new Tab("View suppliers");

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
                    pst = conn.prepareStatement(query);
                    pst.setString(1, supplierNameField.getText());
                    pst.setString(2, phone);
                    pst.setString(3, supplierEmailField.getText());
                    pst.setString(4, supplierAddressField.getText());
                    pst.execute();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Supplier has been registered");
                    alert.showAndWait();
                   // clearFields();
                } catch (Exception ex) {
                    System.err.println("supplier Error: \n" + ex.toString());
                } finally {
                    try {
                        pst.close();
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
        
         
        suppliersTable = new TableView<>();
        final ObservableList<ViewSuppliers> suppliersData = FXCollections.observableArrayList();
        
        //create column supplier name to diplay names of suppliers registered in the database
        TableColumn snameColumn = new TableColumn("Supplier Name");
        snameColumn.setMinWidth(150);
        snameColumn.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));

        //set column for suppliers phone numbers
        TableColumn phoneColumn = new TableColumn("Phone number");
        phoneColumn.setMinWidth(150);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        //set column for displaying suppliers email
        TableColumn emailColumn = new TableColumn("Email");
        emailColumn.setMinWidth(150);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        //set column for displaying suppliers adress
        TableColumn addressColumn = new TableColumn("Address");
        addressColumn.setMinWidth(150);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        //add all columns to the table
        suppliersTable.getColumns().addAll(snameColumn, phoneColumn, emailColumn, addressColumn);
        
        Button viewSuppliers = new Button("Click to view all suppliers");
        viewSuppliers.setStyle("-fx-text-fill:white;");
        viewSuppliers.setOnAction(e -> {
            try {
                
                String query = "select SupplierName,PhoneNumber,Email,Address from supplier";
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
               
                while (rs.next()) {
                    suppliersData.add(new ViewSuppliers(
                            rs.getString("SupplierName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Email"),
                            rs.getString("Address")
                    ));
                    suppliersTable.setItems(suppliersData);
                }
                pst.close();
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

       
        // suppliers.setItems(data);

        //set layout
        VBox supplierTableLayout = new VBox(8);
        supplierTableLayout.setPadding(new Insets(10, 10, 10, 10));
        supplierTableLayout.getChildren().addAll(searchPane, suppliersTable);
        viewSupplier.setContent(supplierTableLayout);

        //adding tabs to tabpane layout
        supplierPane.getTabs().addAll(addSupplier, viewSupplier);
        return supplierPane;
    }
   
    //******************************phone number validation**************************************************
   //spn=suppliers phone number
    public static boolean valPhone(String spn) {
        return spn.charAt(0) == '0' && spn.length() == 10 && spn.matches("[0-9]+");
    }

    //****************************************************************************************************
    
    public void clearFields(){
           
           supplierNameField.clear();
           supplierPhoneField.clear();
           supplierEmailField.clear();
           supplierAddressField.clear();
          
          
        }
}
