
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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class Stock  {
    PreparedStatement pst=null;
    ResultSet rs=null;
    Connection conn=null;
    final ObservableList productlist = FXCollections.observableArrayList();
    final ObservableList productlistValue = FXCollections.observableArrayList();
   
   public TabPane stockTab() {
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
            try {
                String query = "INSERT INTO stock(productName,Quantity) VALUES(?,?)";
                
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
            
        });
        
       VBox stockBox=new VBox(8);
       stockBox.setPadding(new Insets(10,10,10,10));
       stockBox.getChildren().addAll(lbl,stockCombo,quantityField,addStockButton);
       stockBox.setAlignment(Pos.CENTER);
       
       addStock.setContent(stockBox);
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
