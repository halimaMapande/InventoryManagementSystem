
package wholesaleinventorysystem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;




public class ValidationClass {
    TextField fn,ln;
    PasswordField pw;
  public boolean  fNameValidation(){
     Pattern p = Pattern.compile("[a-zA-Z]+");
     Matcher m = p.matcher(fn.getText());
     if(m.find() && m.group().equals(fn.getText())){
         return true;
     }
         else{
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                          alert.setTitle("Information dialog");
                          alert.setHeaderText(null);
                          alert.setContentText("Please enter valid First name");
                         alert.showAndWait();
                 }
   
  return false;  
}
  
  public boolean  lNameValidation(){
     Pattern p = Pattern.compile("[a-zA-Z]+");
     Matcher m = p.matcher(ln.getText());
     if(m.find() && m.group().equals(ln.getText())){
         return true;
     }
         else{
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Information dialog");
             alert.setHeaderText(null);
             alert.setContentText("Please enter valid Last Name");
             alert.showAndWait();
                 }
   
  return false;  
}
  
  public boolean passwordValidation(){
   Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})");
   Matcher m = p.matcher(pw.getText());
   if(m.find() && m.group().equals(pw.getText())){
      return true;
  }
   else{
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("Information dialog");
         alert.setHeaderText(null);
         alert.setContentText("Please enter valid Password");
         alert.showAndWait();   
           }
      
  return false;
  }
 
  public static void processKeyEvent(KeyEvent ev) {
    String c = ev.getCharacter();
    if("1234567890".contains(c)) {}
    else {
        ev.consume();
    }
}
}