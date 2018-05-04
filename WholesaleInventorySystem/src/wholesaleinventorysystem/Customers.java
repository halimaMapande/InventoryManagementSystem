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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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



public class Customers  {
    
     TextField customerFName,customerLName,customerPhone,customerEmail;
     PreparedStatement pst=null;
     ResultSet rs=null;
     Connection conn=null;
     //this collection is for the table
     TableView<ViewCustomers> customerTable = new TableView<>();
     final ObservableList<ViewCustomers> customerData = FXCollections.observableArrayList();
     //this collection is for the combo box
     final ObservableList list = FXCollections.observableArrayList();
     final ObservableList customerlistValue = FXCollections.observableArrayList();
     int id;
     Button deleteCustomer=new Button("Delete");
     Users user=new Users();
     Suppliers suppliers=new Suppliers();
     public TabPane customersTab() {
        customerComboFill();
        viewCustomers();
        
        
        
       //Creating tabs(addCustomer,viewCustomer) in a TabPane layout(customerPane)
        TabPane customersPane = new TabPane();
        Tab addCustomer = new Tab("Add customer");
        Tab viewCustomer = new Tab("View transactions");
        //Add content to add customer tab
        Label addCustomers = new Label("Enter customer details here.");
        addCustomers.setStyle("-fx-text-fill:white;");
        //creating TextFields 
        customerFName = new TextField();
        customerFName.setPromptText("First Name");
        customerFName.setMaxWidth(220);

        customerLName = new TextField();
        customerLName.setPromptText("Last Name");
        customerLName.setMaxWidth(220);

        customerEmail = new TextField();
        customerEmail.setPromptText("Email");
        customerEmail.setMaxWidth(220);
        
        customerPhone = new TextField();
        customerPhone.setPromptText("Phone Number");
        customerPhone.setMaxWidth(220);
        //Button that sends data to db
        Button addCustomerButton = new Button("Save");
        addCustomerButton.setMaxWidth(220);
        addCustomerButton.setStyle("-fx-font-size:16");
        addCustomerButton.setOnAction(e -> {
            String phone = customerPhone.getText();
            if (valPhone(phone) & validateEmail()) {
                try {
                    String query = "INSERT INTO customer(FirstName,LastName,email,phoneNumber) VALUES(?,?,?,?)";
                    conn = DbConnect.getConnection();

                    pst = conn.prepareStatement(query);
                    pst.setString(1, customerFName.getText());
                    pst.setString(2, customerLName.getText());
                    pst.setString(3, customerEmail.getText());
                    pst.setString(4, phone);

                    pst.execute();
                    //clearFields();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Customer has been registered");
                    alert.showAndWait();
                   // clearFields();
                } catch (Exception ex) {
                    System.err.println("customer Error: \n" + ex.toString());
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

        });

        VBox registerBox = new VBox(8);
        registerBox.setAlignment(Pos.CENTER);
        registerBox.getChildren().addAll(addCustomers, customerFName, customerLName, customerEmail, customerPhone, addCustomerButton);
        addCustomer.setContent(registerBox);

        
        deleteCustomer.setStyle("-fx-text-fill:white;");
        deleteCustomer.setStyle("-fx-font-size:16");
        deleteCustomer.setMaxWidth(220);
        deleteCustomer.setDisable(true);
        //this code set a table event that enable user to manipulate the selected row (delete,update)
        customerTable.setOnMouseClicked(e->{
        deleteCustomer.setDisable(false);   
        customerTable.getSelectionModel().getSelectedItem();
        id=customerTable.getSelectionModel().getSelectedItem().getCustomerId();
        });
        
        
        Button updateCustomer=new Button("Update");
        updateCustomer.setStyle("-fx-text-fill:white;");
        updateCustomer.setStyle("-fx-font-size:16");
        updateCustomer.setMaxWidth(220);

        GridPane searchPane = new GridPane();
        searchPane.setPadding(new Insets(10, 10, 10, 10));
        searchPane.setAlignment(Pos.CENTER);
        searchPane.setHgap(8);
        searchPane.setVgap(8);

        searchPane.add(deleteCustomer, 0, 0);
        searchPane.add(updateCustomer, 1, 0);
        
        //creating columns for displaying customers information
        TableColumn<ViewCustomers,Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        TableColumn<ViewCustomers,String> fnameColumn = new TableColumn<>("First Name");
        fnameColumn.setMinWidth(200);
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));

        
        TableColumn<ViewCustomers,String> lnameColumn = new TableColumn<>("Last Name");
        lnameColumn.setMinWidth(200);
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));

        
        TableColumn<ViewCustomers,String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<ViewCustomers,String> phoneColumn = new TableColumn<>("Phone number");
        phoneColumn.setMinWidth(200);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        //add all columns to the table
        customerTable.getColumns().addAll(fnameColumn, lnameColumn, emailColumn, phoneColumn);
        customerTable.setItems(customerData);
        
        
        deleteCustomer.setStyle("-fx-text-fill:white;");
        //An alert that ask user to confirm if he/she is sure of deleting a selected item after clicking delete button
        deleteCustomer.setOnAction(e->{
           Alert confAlert = new Alert(Alert.AlertType.CONFIRMATION);
           confAlert.setTitle("Confirmation dialog");
           confAlert.setHeaderText(null);
           confAlert.setContentText("Are sure you want to deleted this customer?");
            
           Optional<ButtonType> action=confAlert.showAndWait();
           if(action.get()==ButtonType.OK){
                
            //query to delete selected item when delete button is clicked
            try {
                String query="DELETE FROM customer where CustomerId=?";
                pst = conn.prepareStatement(query);
                pst.setInt(1, id);
                pst.execute();
              //An alert that gives info to user if the item is deleted from the db  
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("Product is successful deleted");
                alert.showAndWait();
                //this line make a button unclickable
                deleteCustomer.setDisable(true);
                //refresh table after deleting  item(s)
                customerTable.refresh();
            } 
            catch (SQLException ex) {
                Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        });

        

        //setting layout
        VBox viewbox = new VBox(8);
        viewbox.setPadding(new Insets(10, 10, 10, 10));
        viewbox.getChildren().addAll(searchPane, customerTable);
        viewCustomer.setContent(viewbox);

        customersPane.getTabs().addAll(addCustomer, viewCustomer);
        return customersPane;
    }
     
     public void customerComboFill(){
        try {
            conn=DbConnect.getConnection();
            String query= "select CustomerId, FirstName, LastName from customer";
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            while(rs.next()){
                list.add(rs.getString("FirstName")+ " " +rs.getString("LastName"));
                customerlistValue.add(rs.getString("CustomerId"));
            }
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
      public void viewCustomers() {
          customerData.clear();
            try {
                String query = "SELECT *FROM customer";
                
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
                while (rs.next()) {
                    customerData.add(new ViewCustomers(
                            rs.getInt("CustomerId"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Email"),
                            rs.getString("PhoneNumber")
                         
                    ));
                    customerTable.setItems(customerData);
                    customerTable.refresh();
                }
              //  pst.close();
               // rs.close();
                
            } 
            catch (Exception ex1) {
                System.err.println(ex1);
            }
         customerTable.refresh();
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
      
       public  boolean validateEmail(){
            Pattern p=Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
            Matcher m= p.matcher(customerEmail.getText());
            if(m.find() && m.group().equals(customerEmail.getText())){
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
    
    
    public void clearFields(){
           customerFName.clear();
           customerLName.clear();
           customerPhone.clear();
           customerEmail.clear();
          
        }
}
