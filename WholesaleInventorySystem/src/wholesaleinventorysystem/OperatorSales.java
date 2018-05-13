
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class OperatorSales  {
    PreparedStatement pst;
    ResultSet rs;
    Connection conn;
    ComboBox opCustomerCombo,opProductCombo;
    final ObservableList customerList = FXCollections.observableArrayList();
    final ObservableList customerListValue = FXCollections.observableArrayList();
    
    final ObservableList productList = FXCollections.observableArrayList();
    final ObservableList productListValue = FXCollections.observableArrayList();
    
    TableView<TemporaryKeeper> salesTable = new TableView<>();
    final ObservableList<TemporaryKeeper> salesData = FXCollections.observableArrayList();
    
    int userId;
    
    public OperatorSales(int userId) {
        this.userId = userId;
    }
    
  public TabPane operatorSalesTab() {
      TabPane opSalesPane=new TabPane();
      Tab addSales=new Tab("Add sales");
      
      Label saleLbl=new Label("Fill fields to record sales");
        
      opCustomerCombo = new ComboBox(customerList);
        opCustomerCombo.setPromptText("Select customer ");
        opCustomerCombo.setMaxWidth(220);
        
        opProductCombo = new ComboBox();
        opProductCombo.setPromptText("Select product ");
        opProductCombo.setMaxWidth(220);
        
        TextField quantity=new TextField();
        quantity.setPromptText("Select product ");
        quantity.setMaxWidth(220);
        
        Button send=new Button("Send");
        send.setMaxWidth(220);
        
        VBox vbox=new VBox(8);
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.getChildren().addAll(saleLbl,opCustomerCombo,opProductCombo,quantity,send);
        
        Label label=new Label("Selected items and quantity");
        TableColumn<TemporaryKeeper,String> pnameColumn = new TableColumn<>("PRODUCT NAME");
        pnameColumn.setMinWidth(200);
        pnameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        //column for quantity
        TableColumn<TemporaryKeeper,Integer> quantColumn = new TableColumn<>("QUANTITY");
        quantColumn.setMinWidth(200);
        quantColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        salesTable.getColumns().addAll( pnameColumn, quantColumn);
        salesTable.setItems(salesData);
        
         VBox tableLayout=new VBox();
         tableLayout.setPadding(new Insets(10, 10, 10, 10));
         tableLayout.getChildren().addAll(label,salesTable);
        
        HBox hbox=new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(vbox,tableLayout);
        
        Button dbSave=new Button("Save");
        
        VBox totalLayout=new VBox(8);
        totalLayout.setPadding(new Insets(10, 10, 10, 10));
        totalLayout.setAlignment(Pos.CENTER);
        totalLayout.getChildren().addAll(hbox,dbSave);
        
        
        
        addSales.setContent(totalLayout);
        
        opSalesPane.getTabs().add(addSales);
      return opSalesPane;
  } 
  
  
        public void selectCustomerCombo(){
            customerList.clear();
        try {
            conn=DbConnect.getConnection();
            String query= "select CustomerId, firstName, LastName from Customer";
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            while(rs.next()){
                customerList.add(rs.getString("firstName")+ " " +rs.getString("LastName"));
                customerListValue.add(rs.getString("CustomerId"));
            }
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
           
        public void selectProductCombo(){
            productList.clear();
        try {
            conn=DbConnect.getConnection();
            String query= "select product.ProductId, ProductName as P from Product join stock on "
                    + "product.ProductId=stock.productId where quantity>0";
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            while(rs.next()){
               productList.add(rs.getString("P"));
               productListValue.add(rs.getString("ProductId"));
            }
            
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
