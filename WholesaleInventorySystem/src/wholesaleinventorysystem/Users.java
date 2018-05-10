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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



/**
 *
 * @author josephine
 */
public class Users  {
    
TextField fNameField,lNameField,phoneField,emailField,userField,roleField;
//edit user textfield
   TextField lNameList,fNameList,phoneList,emailList, userFieldList;
    ComboBox   roleComboList, status;
PasswordField passField,passList;
PreparedStatement statement=null;
ResultSet rs=null;
Connection conn=null;
TableView<ViewUsers>  usersTable = new TableView<>();
final ObservableList<ViewUsers> usersData = FXCollections.observableArrayList();
final ObservableList userList = FXCollections.observableArrayList();
final ObservableList userListValue = FXCollections.observableArrayList();
final ObservableList optRole = FXCollections.observableArrayList();
   
public TabPane usersTab() {
    userComboFill();
       viewUsers();
        TabPane userPane = new TabPane();

        Tab addUser = new Tab("Create user account");
        Tab viewUser = new Tab("View users");

        Label fNameLabel = new Label("First Name");
        fNameField = new TextField();
        
        Label lNameLabel = new Label("Last name");
        lNameField = new TextField();
        Label phoneLabel = new Label("Phone number");

        phoneField = new TextField();
        Label emailLabel = new Label("Email");

        emailField = new TextField();
        Label userLabel = new Label("Username");

        userField = new TextField();
        Label passLabel = new Label("Password");

        passField = new PasswordField();
        Label roleLabel = new Label("User role");

        ComboBox roleCombo = new ComboBox();
        roleCombo.getItems().addAll(
                "admin",
                "operator"
        );

        roleCombo.setPromptText("Select user role");

        roleCombo.setEditable(true);
        Button saveUserButton = new Button("Save");
        saveUserButton.setMaxWidth(100);
        saveUserButton.setStyle("-fx-font-size:16");
        saveUserButton.setMaxWidth(220);
        
        saveUserButton.setOnAction(e -> {
          String phone = phoneField.getText();
          String mail=emailField.getText();
            if (valPhone(phone) & validateEmail(mail)) {
            try {
                String query = "INSERT INTO Users(FirstName,LastName,PhoneNumber,Email,Username,Password,Role) VALUES(?,?,?,?,?,?,?)";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                statement.setString(1, fNameField.getText());
                statement.setString(2, lNameField.getText());
                statement.setString(3, phone);
                statement.setString(4, emailField.getText());
                statement.setString(5, userField.getText());
                statement.setString(6, passField.getText());
                statement.setString(7, roleCombo.getSelectionModel().getSelectedItem().toString());
                statement.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("User has been created");
                alert.showAndWait();
                clearFields();
            } catch (Exception ex) {
                System.err.println("Error: \n" + ex.toString());
            } finally {
                try {
                    statement.close();
                    //conn.close();
                } catch (Exception ex) {
                }

            }
            }
            viewUsers();
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 5, 5, 5));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(8);

        //add to layout gridpane
        gridPane.add(fNameLabel, 0, 0, 1, 1);
        gridPane.add(fNameField, 1, 0, 1, 1);
        gridPane.add(lNameLabel, 0, 1, 1, 1);
        gridPane.add(lNameField, 1, 1, 1, 1);
        gridPane.add(phoneLabel, 0, 2, 1, 1);
        gridPane.add(phoneField, 1, 2, 1, 1);
        gridPane.add(emailLabel, 0, 3, 1, 1);
        gridPane.add(emailField, 1, 3, 1, 1);
        gridPane.add(userLabel, 0, 4, 1, 1);
        gridPane.add(userField, 1, 4, 1, 1);
        gridPane.add(passLabel, 0, 5, 1, 1);
        gridPane.add(passField, 1, 5, 1, 1);
        gridPane.add(roleLabel, 0, 6, 1, 1);
        gridPane.add(roleCombo, 1, 6, 1, 1);
        gridPane.add(saveUserButton, 1, 7, 1, 1);

        addUser.setContent(gridPane);
        
        //===============================table for displaying all users===================================
       
        TableColumn<ViewUsers,Integer> uidColumn = new TableColumn<>("ID");
        uidColumn.setMinWidth(150);
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        
        TableColumn<ViewUsers,String> fnameColumn = new TableColumn<>("FIRST NAME");
        fnameColumn.setMinWidth(150);
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));

        TableColumn<ViewUsers,String> lnameColumn = new TableColumn<>("LAST NAME");
        lnameColumn.setMinWidth(150);
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));

        TableColumn<ViewUsers,String> phoneColumn = new TableColumn<>("PHONENUMBER");
        phoneColumn.setMinWidth(150);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<ViewUsers,String> emailColumn = new TableColumn<>("EMAIL");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<ViewUsers,String> userColumn = new TableColumn<>("USERNAME");
        userColumn.setMinWidth(100);
        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<ViewUsers,String> passColumn = new TableColumn<>("PASSWORD");
        passColumn.setMinWidth(100);
        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<ViewUsers,String> roleColumn = new TableColumn<>("ROLE");
        roleColumn.setMinWidth(100);
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        usersTable.getColumns().addAll(fnameColumn, lnameColumn, phoneColumn, emailColumn, userColumn, passColumn, roleColumn);
         //=================================end of users table===================================================
        Label editLbl=new Label("Edit details of a selected user");
        fNameList = new TextField();
        fNameList.setMaxWidth(220);
        fNameList.setPromptText("First Name");
        
        lNameList = new TextField();
        lNameList.setMaxWidth(220);
        lNameList.setPromptText("Last Name");
       
        phoneList = new TextField();
        phoneList.setMaxWidth(220);
        phoneList.setPromptText("Phone Number");

         emailList = new TextField();
        emailList.setMaxWidth(220);
        emailList.setPromptText("Email");
        
        userFieldList = new TextField();
        userFieldList.setMaxWidth(220);
        userFieldList.setPromptText("UserName");
        

        passList = new PasswordField();
        passList.setMaxWidth(220);
        passList.setPromptText("Password");
        

        roleComboList = new ComboBox();
        roleComboList.setMaxWidth(220);
        roleComboList.setPromptText("Role");
        roleComboList.getItems().addAll(
                "admin",
                "operator"
        );
        
         status=new ComboBox();
        status.setMaxWidth(220);
        status.setPromptText("Status");
        status.getItems().addAll(
                "active",
                "inactive"
        );
        
        Button updateUserButton = new Button("Update");
        updateUserButton.setMaxWidth(100);
        updateUserButton.setStyle("-fx-font-size:14");
        //table event for updation
        updateUserButton.setDisable(true);
        usersTable.setOnMouseClicked(e->{
          
         updateUserButton.setDisable(false);
               fNameList.setText(usersTable.getSelectionModel().getSelectedItem().getFName());
               lNameList.setText(usersTable.getSelectionModel().getSelectedItem().getLName());
               phoneList.setText(usersTable.getSelectionModel().getSelectedItem().getPhoneNumber());
               emailList.setText(usersTable.getSelectionModel().getSelectedItem().getEmail());
               userFieldList.setText(usersTable.getSelectionModel().getSelectedItem().getUsername());
               passList.setText(usersTable.getSelectionModel().getSelectedItem().getPassword());
       //fill the combolist the selected element in table
               for (Object opt : optRole) {
                   if (opt.equals(usersTable.getSelectionModel().getSelectedItem().getRole())) {
                        


                      roleComboList.getSelectionModel().select(opt);
                        System.out.println(opt.toString());
                   }
                  
                
            }
               //roleComboList.getSelectionModel().getSelectedItem(usersTable.getRole());
              
        });
        
        
        
        
        GridPane fieldsPane=new GridPane();
        fieldsPane.setPadding(new Insets(10, 5, 5, 5));
        fieldsPane.setAlignment(Pos.CENTER);
        fieldsPane.setHgap(5);
        fieldsPane.setVgap(8);
       
       fieldsPane.add(fNameList, 0, 0);
       fieldsPane.add(lNameList, 1, 0);
       fieldsPane.add(phoneList, 0, 1);
       fieldsPane.add(emailList, 1, 1);
       fieldsPane.add(userFieldList, 0, 2);
       fieldsPane.add(passList, 1, 2);
       fieldsPane.add(roleComboList, 0, 3);
       fieldsPane.add(status, 1, 3);
       fieldsPane.add(updateUserButton, 0, 4, 1, 2);
        //update user
        updateUserButton.setOnAction(e -> {
          String uphone = phoneList.getText();
           String mail=emailList.getText();
            System.out.println(uphone);
            if (valPhone(uphone) && validateEmail(mail)) {
            try {
                String query = "UPDATE Users SET FirstName=?,LastName=?,PhoneNumber=?,Email=?,Username=?,Password=?,Role=? where Email=?";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
               
                statement.setString(1,fNameList .getText());
                statement.setString(2, lNameList.getText());
                statement.setString(3, uphone);
                statement.setString(4, emailList.getText());
                statement.setString(5, userFieldList.getText());
                statement.setString(6,passList.getText());
                statement.setString(7, roleComboList.getSelectionModel().getSelectedItem().toString());
                statement.setString(8, emailList.getText());
                statement.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("User has been updated");
                alert.showAndWait();
                clearFields();
            } catch (Exception ex) {
                System.err.println("Error: \n" + ex.toString());
            } finally {
                try {
                    statement.close();
                    //conn.close();
                } catch (Exception ex) {
                }

            }
            }
            viewUsers();
        });


        //
        VBox listBox = new VBox(10);
        listBox.setPadding(new Insets(10, 10, 10, 10));
        listBox.getChildren().addAll(usersTable,editLbl,fieldsPane);
        listBox.setAlignment(Pos.CENTER);
        viewUser.setContent(listBox);
        userPane.getTabs().addAll(addUser, viewUser);
        return userPane;
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
                return false;
        }
       
    }

    //****************************************************************************************************
    public void viewUsers(){
        usersData.clear();
            try {
                String query = "select userId,FirstName,LastName,PhoneNumber,Email,Username,Password,Role from users";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                rs = statement.executeQuery();
                while (rs.next()) {
                    usersData.add(new ViewUsers(
                            rs.getInt("userId"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Email"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Role")
                    ));
                    usersTable.setItems(usersData);
                }
                //statement.close();
                //rs.close();
                //conn.close();
            } catch (Exception ex1) {
                System.err.println(ex1);
            }
             usersTable.refresh();
        }

        public  boolean validateEmail(String mail){
            Pattern p=Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
            Matcher m= p.matcher(mail);
            if(m.find() && m.group().equals(mail)){
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
    
       
        
        public void userComboFill() {
        try {
            conn = DbConnect.getConnection();
            String query = "select DISTINCT Role from users ";
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                optRole.add(rs.getString("Role"));
                //roleOptionsValue.add(rs.getString("UserId"));
            }
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabsClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
        
    public void clearFields(){
           
           fNameField.clear();
           lNameField.clear();
           phoneField.clear();
           emailField.clear();
           userField.clear();
           passField.clear();
           roleField.clear();
          
          
        }
}