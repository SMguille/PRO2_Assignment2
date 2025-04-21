package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class LogInView
{
  @FXML private TextField logInField;
  @FXML
  public void onLogIn(){
    logInField.getText();
  }
}
