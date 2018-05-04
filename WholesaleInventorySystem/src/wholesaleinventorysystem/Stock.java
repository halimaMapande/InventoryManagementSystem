
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Stock  {
    PreparedStatement pst=null;
    ResultSet rs=null;
    Connection conn=null;
    final ObservableList productlist = FXCollections.observableArrayList();
    final ObservableList productlistValue = FXCollections.observableArrayList();
     TableView<TemporaryKeeper> table = new TableView<>();
     final ObservableList<TemporaryKeeper> data = FXCollections.observableArrayList();
     VBox tableLayout=null;
   
   public TabPane stockTab() {
       //tableLayout=null;
       table.getItems().clear();
        selectProductCombo();
         
        TabPane stockPane = new TabPane();
        Tab addStock = new Tab("Add customer");
        Tab viewStock = new Tab("View stock");
        
        Label lbl=new Label("Add products to stock");
        lbl.setStyle("-fx-text-fill:white;");
        
        ComboBox stockCombo = new ComboBox(productlist);
        stockCombo.setPromptText("Select product ");
        stockCombo.setMaxWidth(220);
        
        TextField quantityField=new TextField();
        quantityField.setMaxWidth(220);
        
        Button addStockButton=new Button("Add stock");
        addStockButton.setMaxWidth(220);
        addStockButton.setStyle("-fx-text-fill:white;");
        addStockButton.setOnAction(e -> {
            
            //update data to the stock
            
            try {
                table.getItems().clear();
                TemporaryKeeper a =new TemporaryKeeper(null,1);
                if (a.stockverify(stockCombo.getSelectionModel().getSelectedItem().toString())) {
                   //updaqte stock
                    int qty=Integer.parseInt(quantityField.getText());
                    String query = "UPDATE stock set quantity=quantity+? where productid=?";
                
                pst = conn.prepareStatement(query);
                String productName = stockCombo.getSelectionModel().getSelectedItem().toString();
                int productValue = productlist.indexOf(productName);
                int id = new Integer(productlistValue.get(productValue).toString());
                
                pst.setInt(1, qty);
                 pst.setInt(2, id);
                pst.execute();
               
                 
                } 
                else{
                //insert
                    try {
                String query = "INSERT INTO stock(productId,quantity) VALUES(?,?)";
                
                pst = conn.prepareStatement(query);
                String productName = stockCombo.getSelectionModel().getSelectedItem().toString();
                int productValue = productlist.indexOf(productName);
                int id = new Integer(productlistValue.get(productValue).toString());
                
                pst.setInt(1, id);
                pst.setString(2, quantityField.getText());
               
                pst.execute();
                
                //clearFields();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("Product is added to stock");
                alert.showAndWait();
                
            } 
            catch (Exception ex) {
                System.err.println("stock Error: \n" + ex.toString());
            } finally {
                try {
                    pst.close();
                   // conn.close();
                } catch (Exception ex) {
                }

            }
                }
            conn=DbConnect.getConnection();
            String query= "select ProductName,quantity from Product join stock on "
                    + "Product.productId=stock.productId";
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            while(rs.next()){
                 data.add(new TemporaryKeeper(rs.getString("ProductName"),rs.getInt("quantity")));
             table.setItems(data);
              
            }
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        });
        Label label=new Label("Item available in stock");
        label.setStyle("-fx-text-fill:white");
        //column for productname
        TableColumn<TemporaryKeeper,String> pnameColumn = new TableColumn<>("PRODUCT NAME");
        pnameColumn.setMinWidth(200);
        pnameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        //column for quantity
        TableColumn<TemporaryKeeper,Integer> quantColumn = new TableColumn<>("QUANTITY");
        quantColumn.setMinWidth(200);
        quantColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        table.getColumns().addAll( pnameColumn, quantColumn);
        table.setItems(data);
        //itemin stock\
        try {
            conn=DbConnect.getConnection();
            String query= "select ProductName,quantity from Product join stock on "
                    + "Product.productId=stock.productId";
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            while(rs.next()){
                 data.add(new TemporaryKeeper(rs.getString("ProductName"),rs.getInt("quantity")));
           
              
            }
              table.setItems(data);
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        //end
        //layout for table alone
        
        tableLayout=new VBox();
        tableLayout.setPadding(new Insets(10, 10, 10, 10));
        tableLayout.getChildren().addAll(label,table);
       VBox stockBox=new VBox(8);
       stockBox.setPadding(new Insets(10,10,10,10));
       stockBox.getChildren().addAll(lbl,stockCombo,quantityField,addStockButton);
       stockBox.setAlignment(Pos.TOP_LEFT);
       HBox hbox =new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(stockBox,tableLayout);
       addStock.setContent(hbox);
       stockPane.getTabs().addAll(addStock, viewStock);
       return stockPane;
   }
   
   public void selectProductCombo(){
        try {
            conn=DbConnect.getConnection();
            String query= "select ProductId, ProductName as P from Product";
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            while(rs.next()){
               productlist.add(rs.getString("P"));
               productlistValue.add(rs.getString("ProductId"));
            }
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
