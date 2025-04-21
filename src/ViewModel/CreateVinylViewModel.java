package ViewModel;

import Model.Vinyl;
import Network.NetworkClient;
import Network.Request;
import Network.VinylDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CreateVinylViewModel {

  private final StringProperty title;
  private final StringProperty artist;
  private final StringProperty releaseYear;

  private final NetworkClient network;

  public CreateVinylViewModel(NetworkClient network) {
    this.network = network;
    this.title = new SimpleStringProperty();
    this.artist = new SimpleStringProperty();
    this.releaseYear = new SimpleStringProperty();
  }

  public void addVinyl() {
    try {
      int year = Integer.parseInt(releaseYear.get());
      Request req = new Request("addVinyl", "0", VinylDTO.fromModel(new Vinyl(title.get(), artist.get(), year)));
      network.sendRequest(req);
    } catch (NumberFormatException e) {
      System.err.println("Invalid year format: " + releaseYear.get());
    }
  }

  public StringProperty releaseYearProperty() {
    return releaseYear;
  }

  public StringProperty artistProperty() {
    return artist;
  }

  public StringProperty titleProperty() {
    return title;
  }
}
