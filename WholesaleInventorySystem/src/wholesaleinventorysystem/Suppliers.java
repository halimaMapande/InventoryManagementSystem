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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


/**
 *
 * @author josephine
 */
public class Suppliers {
    TextField supplierNameField,supplierPhoneField,emailField,supplierAddressField;
    PreparedStatement pst=null;
    ResultSet rs=null;
    Connection conn=null;
    TableView<ViewSuppliers> suppliersTable = new TableView<>();
    final ObservableList<ViewSuppliers> suppliersData = FXCollections.observableArrayList();
    Users users=new Users();
   
    public TabPane suppliersTab() {
        DropShadow shadow=new DropShadow();
       conn = DbConnect.getConnection();
       viewSuppliers();       
        TabPane supplierPane = new TabPane();
        Tab addSupplier = new Tab("Add supplier");
        Tab viewSupplier = new Tab("View suppliers");

        Label supplierLbl = new Label("Enter Suppliers details to register");
        supplierNameField = new TextField();
        supplierNameField.setMaxWidth(220);
        supplierNameField.setPromptText("Supplier name");
        supplierPhoneField= new TextField();
        supplierPhoneField.setMaxWidth(220);
        supplierPhoneField.setPromptText("Phone number");
        emailField = new TextField();
        emailField.setMaxWidth(220);
        emailField.setPromptText("Email address");
        supplierAddressField = new TextField();
        supplierAddressField.setMaxWidth(220);
        supplierAddressField.setPromptText("Address");
        Button addSuppliers = new Button("save");
        addSuppliers.setMaxWidth(220);
        addSuppliers.setStyle("-fx-font-size:16");
        
        addSuppliers.setOnMouseEntered(e->{
            addSuppliers.setEffect(shadow);
        });
        addSuppliers.setOnMouseExited(e->{
            addSuppliers.setEffect(null);
        });
        
        addSuppliers.setOnAction(e -> {
            String phone = supplierPhoneField.getText();
            if (valPhone(phone) & validateEmail()) {
                try {
                    String query = "INSERT INTO Supplier(SupplierName,PhoneNumber,Email,Address) VALUES(?,?,?,?)";
                    pst = conn.prepareStatement(query);
                    pst.setString(1, supplierNameField.getText());
                    pst.setString(2, phone);
                    pst.setString(3, emailField.getText());
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
                        //conn.close();
                    } catch (Exception ex) {
                    }

                }
            } /*else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("invalid phone number");
                alert.showAndWait();
            }*/
            viewSuppliers();
        });

        VBox vbox1 = new VBox(10);
        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox1.getChildren().addAll(supplierLbl, supplierNameField, supplierPhoneField, emailField, supplierAddressField, addSuppliers);
        vbox1.setAlignment(Pos.CENTER);
        addSupplier.setContent(vbox1);

        //create column supplier name to diplay names of suppliers registered in the database
        TableColumn<ViewSuppliers,String> snameColumn = new TableColumn<>("SUPPLIER NAME");
        snameColumn.setMinWidth(150);
        snameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));

        //set column for suppliers phone numbers
        TableColumn<ViewSuppliers,String> phoneColumn = new TableColumn<>("PHONENUMBER");
        phoneColumn.setMinWidth(150);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        //set column for displaying suppliers email
        TableColumn<ViewSuppliers,String> emailColumn = new TableColumn<>("EMAIL");
        emailColumn.setMinWidth(150);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        //set column for displaying suppliers adress
        TableColumn<ViewSuppliers,String> addressColumn = new TableColumn<>("ADDRESS");
        addressColumn.setMinWidth(150);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        //add all columns to the table
        suppliersTable.getColumns().addAll(snameColumn, phoneColumn, emailColumn, addressColumn);
        
        //supplier info updation fields
        TextField updateName=new TextField();
        updateName.setMaxWidth(220);
        updateName.setPromptText("Supplier name");
        
        TextField updatePhone=new TextField();
        updatePhone.setMaxWidth(220);
        updatePhone.setPromptText("Phone number");
       
        TextField updateEmail=new TextField();
        updateEmail.setMaxWidth(220);
        updateEmail.setPromptText("Email");
        
        TextField updateAddress=new TextField();
        updateAddress.setMaxWidth(220);
        updateAddress.setPromptText("Address");
        
        Button btn=new Button("Update");
        GridPane supplierGrid=new GridPane();
        supplierGrid.setPadding(new Insets(10, 10, 10, 10));
        supplierGrid.setHgap(10);
        supplierGrid.setVgap(10);
        
        supplierGrid.add(updateName, 0, 0);
        supplierGrid.add(updatePhone, 1, 0);
        supplierGrid.add(updateEmail, 2, 0);
        supplierGrid.add(updateAddress, 0, 1);
        supplierGrid.add(btn, 1, 1);
        
        btn.setDisable(true);
        suppliersTable.setOnMouseClicked(e->{
       
         btn.setDisable(false);
               updateName.setText(suppliersTable.getSelectionModel().getSelectedItem().getSupplierName());
               updatePhone.setText(suppliersTable.getSelectionModel().getSelectedItem().getPhoneNumber());
               updateEmail.setText(suppliersTable.getSelectionModel().getSelectedItem().getEmail());
               updateAddress.setText(suppliersTable.getSelectionModel().getSelectedItem().getAddress());
        });
        
        btn.setOnAction(e->{
            
           try {
               conn=DbConnect.getConnection();
               String query="UPDATE supplier SET SupplierName=?,PhoneNumber=?,Email=?,Address=? WHERE Email=?";
               pst=conn.prepareStatement(query);
              
               pst.setString(1,updateName.getText());
               pst.setString(2, updatePhone.getText());
               pst.setString(3,updateEmail.getText());
               pst.setString(4, updateAddress.getText());
               pst.setString(5,updateEmail.getText());
               pst.execute();
               Alert updateAlert=new Alert(Alert.AlertType.INFORMATION);
               updateAlert.setTitle("Information dialog");
               updateAlert.setHeaderText(null);
               updateAlert.setContentText("Supplier information have been updated");
               updateAlert.showAndWait();
               clearFields();
               pst.close();
           } catch (SQLException ex) {
               Logger.getLogger(Suppliers.class.getName()).log(Level.SEVERE, null, ex);
           }
           viewSuppliers();
        });
               
       
        //set layout
        VBox supplierTableLayout = new VBox(8);
        supplierTableLayout.setPadding(new Insets(10, 10, 10, 10));
        supplierTableLayout.getChildren().addAll(suppliersTable,supplierGrid);
        viewSupplier.setContent(supplierTableLayout);

        //adding tabs to tabpane layout
        supplierPane.getTabs().addAll(addSupplier, viewSupplier);
        return supplierPane;
    }
   
    //******************************phone number validation**************************************************
   
   public static boolean valPhone(String pn) {
        if(pn.charAt(0) == '0' && pn.length() == 10 && pn.matches("[0-9]+")){
            return true;
        }
        else{
             Alert alert1=new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Information dialog");
                alert1.setHeaderText(null);
                alert1.setContentText("Invalid phonenumber");
                alert1.showAndWait();
        }
        return false;
    }

    //****************************************************************************************************
     public void viewSuppliers() {
            try {
                suppliersData.clear();
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
            
        }

      public  boolean validateEmail(){
            Pattern p=Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
            Matcher m= p.matcher(emailField.getText());
            if(m.find() && m.group().equals(emailField.getText())){
                        return true;
            }
            else{
               Alert alert1=new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Information dialog");
                alert1.setHeaderText(null);
                alert1.setContentText("Invalid email address");
                alert1.showAndWait();
            }
            return false;
        }
      
      public void refreshSuppliersTable(){
          
      }
      
    
    public void clearFields(){
           
           supplierNameField.clear();
           supplierPhoneField.clear();
           emailField.clear();
           supplierAddressField.clear();
          
          
        }
}
