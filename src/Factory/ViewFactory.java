package Factory;

import View.VinylListView;
import View.CreateVinylView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory
{
  private VinylListView vinylListView;
  private CreateVinylView createVinylView;
  private ViewModelFactory viewModelFactory;

  private Stage primaryStage;
  private FXMLLoader fxmlLoader;


  public ViewFactory(ViewModelFactory viewModelFactory, Stage primaryStage)
  {
    this.viewModelFactory = viewModelFactory;
    this.primaryStage = primaryStage;
  }

  public VinylListView getVinylListView() throws IOException
  {
    if(vinylListView == null)
    {
      fxmlLoader = new FXMLLoader(getClass().getResource("../View/VinylListView.fxml"));
      fxmlLoader.setControllerFactory(controllerClass -> new VinylListView(viewModelFactory.getVinylListViewModel()));

      Scene vinylListScene = new Scene(fxmlLoader.load());
      primaryStage.setTitle("View Vinyls");
      primaryStage.setScene(vinylListScene);
      primaryStage.show();
      vinylListView = fxmlLoader.getController();
    }
    return vinylListView;
  }

  public CreateVinylView getCreateVinylView() throws IOException
  {
    if(createVinylView == null)
    {
      fxmlLoader = new FXMLLoader(getClass().getResource("../View/CreateVinylView.fxml"));
      fxmlLoader.setControllerFactory(controllerClass -> new CreateVinylView(viewModelFactory.getCreateVinylViewModel()));

      Scene createVinylScene = new Scene(fxmlLoader.load());
      Stage secondaryStage = new Stage();
      secondaryStage.setTitle("Create Vinyl");
      secondaryStage.setScene(createVinylScene);
      secondaryStage.show();
      createVinylView = fxmlLoader.getController();
    }
    return createVinylView;
  }


}
