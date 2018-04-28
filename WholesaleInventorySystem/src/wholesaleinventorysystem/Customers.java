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
//import static wholesaleinventorysystem.TabsClass.valPhone;


/**
 *
 * @author josephine
 */
public class Customers  {
    
     TextField customerFName,customerLName,customerPhone,customerEmail;
     PreparedStatement pst=null;
     ResultSet rs=null;
     Connection conn=null;
     final ObservableList list = FXCollections.observableArrayList();
     final ObservableList customerlistValue = FXCollections.observableArrayList();
     
     public TabPane customersTab() {
        customerComboFill();
        TabPane customersPane = new TabPane();
        Tab addCustomer = new Tab("Add customer");
        Tab viewCustomer = new Tab("View transactions");
        //Add content to add customer tab
        Label addCustomers = new Label("Enter customer details here.");
        addCustomers.setStyle("-fx-text-fill:white;");

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

        Button addCustomerButton = new Button("Save");
        addCustomerButton.setMaxWidth(100);
        addCustomerButton.setStyle("-fx-font-size:16");
        addCustomerButton.setOnAction(e -> {
            String phone = customerPhone.getText();
            if (valPhone(phone)) {
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

        VBox registerBox = new VBox(8);
        registerBox.setAlignment(Pos.CENTER);
        registerBox.getChildren().addAll(addCustomers, customerFName, customerLName, customerEmail, customerPhone, addCustomerButton);
        addCustomer.setContent(registerBox);

        //set content to view transactions
        
       
        Label searchLbl = new Label("Customer Name");
        searchLbl.setStyle("-fx-text-fill:white;");
        ComboBox comboBox = new ComboBox(list);
        comboBox.setPromptText("Select customer");

        comboBox.setEditable(true);
        Button searchButton = new Button("search");

        GridPane searchPane = new GridPane();
        searchPane.setPadding(new Insets(10, 10, 10, 10));
        searchPane.setAlignment(Pos.CENTER);
        searchPane.setHgap(8);
        searchPane.setVgap(8);

        searchPane.add(searchLbl, 0, 0);
        searchPane.add(comboBox, 1, 0);
        searchPane.add(searchButton, 2, 0);

        //create table to display transaction for a selected customer
        TableView transTable = new TableView<>();
        final ObservableList<ViewCustomers> customerData = FXCollections.observableArrayList();
        TableColumn fnameColumn = new TableColumn("First Name");
        fnameColumn.setMinWidth(200);
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));

        //set column for product prices
        TableColumn lnameColumn = new TableColumn("Last Name");
        lnameColumn.setMinWidth(200);
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //set column for product quantity
        TableColumn emailColumn = new TableColumn("Email");
        emailColumn.setMinWidth(100);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn phoneColumn = new TableColumn("Phone number");
        phoneColumn.setMinWidth(100);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        //add all columns to the table
        transTable.getColumns().addAll(fnameColumn, lnameColumn, emailColumn, phoneColumn);
        transTable.setItems(customerData);

        //setting layout
        VBox viewbox = new VBox(8);
        viewbox.setPadding(new Insets(10, 10, 10, 10));
        viewbox.getChildren().addAll(searchPane, transTable);
        viewCustomer.setContent(viewbox);

        customersPane.getTabs().addAll(addCustomer, viewCustomer);
        return customersPane;
    }
     
     public void customerComboFill(){
        try {
            conn=DbConnect.getConnection();
            String query= "select CustomerId, firstName, LastName from Customer";
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            while(rs.next()){
                list.add(rs.getString("firstName")+ " " +rs.getString("LastName"));
                customerlistValue.add(rs.getString("CustomerId"));
            }
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     //******************************phone number validation**************************************************
    public static boolean valPhone(String in) {
        return in.charAt(0) == '0' && in.length() == 10 && in.matches("[0-9]+");
    }

    //****************************************************************************************************
    
    public void clearFields(){
           customerFName.clear();
           customerLName.clear();
           customerPhone.clear();
           customerEmail.clear();
          
        }
}
