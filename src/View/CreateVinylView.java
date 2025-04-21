package View;

import ViewModel.CreateVinylViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateVinylView
{
  @FXML private TextField titleTextField;
  @FXML private TextField artistTextField;
  @FXML private TextField releaseYearTextField;

  private CreateVinylViewModel createVinylViewModel;

  public CreateVinylView(CreateVinylViewModel createVinylViewModel){
    this.createVinylViewModel = createVinylViewModel;
  }


  @FXML
  public void onAddVinyl(){
    createVinylViewModel.addVinyl();
  }

  public void initialize() {
    titleTextField.textProperty().bindBidirectional(createVinylViewModel.titleProperty());
    artistTextField.textProperty().bindBidirectional(createVinylViewModel.artistProperty());
    releaseYearTextField.textProperty().bindBidirectional(createVinylViewModel.releaseYearProperty());
  }


}
