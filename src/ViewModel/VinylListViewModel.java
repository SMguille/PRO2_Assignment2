package ViewModel;

import Factory.ModelFactory;
import Factory.ViewFactory;
import Factory.ViewModelFactory;
import Model.Model;
import Network.Request;
import Network.VinylDTO;
import Network.NetworkClient;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.Vinyl;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class VinylListViewModel
{
  private final ObservableList<Vinyl> vinyls = FXCollections.observableArrayList();
  private final StringProperty userId = new SimpleStringProperty();
  private NetworkClient network;

  public VinylListViewModel(NetworkClient network)
  {
    this.network = network;
  }

  /** Called by NetworkClient on the JavaFX thread whenever a full list arrives */
  public void onServerFullList(List<VinylDTO> dtos)
  {
    List<Vinyl> list = dtos.stream().map(VinylDTO::toModel).toList();
    vinyls.setAll(list);
  }

  public void reserveVinyl(Vinyl v)
  {
    if(userId.getValue() == null || Objects.equals(userId.getValue(), "")){
      userId.setValue("0");
    }
    network.sendRequest(new Request("reserve", userId.get(), VinylDTO.fromModel(v)));
  }
  // ... similarly for borrow, return, remove
  public void removeVinyl(Vinyl v)
  {
    network.sendRequest(new Request("remove", userId.get(), VinylDTO.fromModel(v)));
  }
  public void borrowVinyl(Vinyl v)
  {
    network.sendRequest(new Request("borrow", userId.get(), VinylDTO.fromModel(v)));
  }
  public void returnVinyl(Vinyl v)
  {
    network.sendRequest(new Request("return", userId.get(), VinylDTO.fromModel(v)));
  }



  public ObservableList<Vinyl> getVinyls() { return vinyls; }
  public StringProperty userIdProperty() { return userId; }
}
/*
public class VinylListViewModel {
  private final NetworkClient network;
  private final ObservableList<Vinyl> vinyls = FXCollections.observableArrayList();
  private final StringProperty userId = new SimpleStringProperty();

  public VinylListViewModel(NetworkClient network) {
    this.network = network;
    // Listen for full‑list broadcasts from the server:
    network.setOnFullListReceived(this::replaceLocalList);
  }

  private void replaceLocalList(List<VinylDTO> dtos) {
    var newList = dtos.stream()
        .map(Vinyl::fromDTO)
        .toList();
    Platform.runLater(() -> vinyls.setAll(newList));
  }

  public void reserveVinyl(Vinyl v) {
    network.sendRequest(new Request("reserve", userId.get(), VinylDTO.fromModel(v)));
  }
  // similarly for borrow, return, remove...

  public ObservableList<Vinyl> getVinyls() { return vinyls; }
  public StringProperty userIdProperty() { return userId; }


  public void removeVinyl(Vinyl vinyl){
    model.removeVinyl(vinyl, userId.getValue());
  }
  public void reserveVinyl(Vinyl vinyl){
    if(userId.getValue() == null || Objects.equals(userId.getValue(), "")){
      userId.setValue("0");
    }
    model.reserveVinyl(vinyl, userId.getValue());
  }
  public void borrowVinyl(Vinyl vinyl){
    model.borrowVinyl(vinyl, userId.getValue());
  }
  public void returnVinyl(Vinyl vinyl){
    model.returnVinyl(vinyl, userId.getValue());
  }


}
 */