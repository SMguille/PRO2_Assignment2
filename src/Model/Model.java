package Model;

import Network.*;
import Util.JsonUtil;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Model implements PropertyChangeSubject
{
  private List<Vinyl> vinylList;
  private PropertyChangeSupport support;
  private Server server;

  public Model()
  {
    this.vinylList = new ArrayList<>();
    this.support = new PropertyChangeSupport(this);
    this.server = new Server(this);
    vinylList.add(new Vinyl("Abbey Road", "The Beatles", 1969));
    vinylList.add(new Vinyl("Dark Side of the Moon", "Pink Floyd", 1973));
    vinylList.add(new Vinyl("Led Zeppelin IV", "Led Zeppelin", 1971));
    vinylList.add(new Vinyl("Rumours", "Fleetwood Mac", 1977));
    vinylList.add(new Vinyl("The Wall", "Pink Floyd", 1979));
  }

  public List<Vinyl> getVinylList()
  {
    return vinylList;
  }

  public void addVinyl(String title, String artist, int releaseYear) {
    vinylList.add(new Vinyl(title, artist, releaseYear));
    support.firePropertyChange("listChanged", null, vinylList);
    Logger.getInstance().log("Vinyl Added");
  }

  public synchronized void removeVinyl(Vinyl vinyl, String removingUserId)
  {
    if (vinyl.getCurrentState().toString().equals("AvailableState")
        || vinyl.getCurrentState().toString().equals("ReservedState") && vinyl.getReservedUserId().equals(removingUserId))
    //When the vinyl is borrowed it cannot be deleted since it is outside the vinyl store
    {
      vinylList.remove(vinyl); //Anyone or the user that has reserved the vinyl can remove a vinyl that is in the store (It is still in the shop so he cannot steal it)
      support.firePropertyChange("listChanged", null, vinylList);
      Logger.getInstance().log("Vinyl Deleted");
    }
    else{
      vinyl.setMarkedForRemoval(true);
    }
}

  public synchronized void reserveVinyl(Vinyl vinyl, String userId) {
    if (vinyl.getCurrentState().toString().equals("AvailableState")
        || vinyl.getCurrentState().toString().equals("BorrowedState")
        || (vinyl.getCurrentState().toString().equals("BorrowedReservedState")
        && vinyl.getReservedUserId().equals(userId))) {

      vinyl.reserve(); // reserve directly or Change to borrowedReserved if it was already borrowed
      vinyl.setReservedUserId(userId);
      Logger.getInstance().log("Vinyl Reserved");

      // Fire event after unlocking
      fireListChangedEvent();
    }
    else { // if it's already reserved or borrowed
      if (vinyl.getReservedUserId() != null && vinyl.getReservedUserId().equals(userId)) {
        vinyl.changeToAvailable(); // cancel reservation
        Logger.getInstance().log("Vinyl Reservation Canceled");

        // Fire event after unlocking
        fireListChangedEvent();
      }
    }
  }

  public synchronized void borrowVinyl(Vinyl vinyl, String userId)
  {
    if (vinyl.getCurrentState().toString().equals("AvailableState") //if it is available change directly
        || vinyl.getCurrentState().toString().equals("ReservedState")
        && vinyl.getReservedUserId().equals(userId)) //if it is reserved, the user that made the reservation can borrow
    {
      vinyl.setBorrowedUserId(userId);
      vinyl.borrow();
      support.firePropertyChange("listChanged", null, vinylList);
      Logger.getInstance().log("Vinyl borrowed");
    }
  }

  public synchronized void returnVinyl(Vinyl vinyl, String userId){
    if((vinyl.getCurrentState().toString().equals("BorrowedState") || vinyl.getCurrentState().toString().equals("BorrowedReservedState")) //if it is borrowed, the person that is borrowing can return the vinyl
        && vinyl.getBorrowedUserId().equals(userId)){
      vinyl.onReturn();
      support.firePropertyChange("listChanged", null, vinylList);
      Logger.getInstance().log("Vinyl Returned");
    }
  }

  public List<VinylDTO> getVinylListDTO() {
    List<VinylDTO> dtoList = new ArrayList<>();
    for (Vinyl vinyl : vinylList) {
      String borrowedUserId = vinyl.getBorrowedUserId();
      String reservedUserId = vinyl.getReservedUserId();
      String currentState = vinyl.getCurrentState().asString();

      dtoList.add(new VinylDTO(vinyl.getTitle(), vinyl.getArtist(), vinyl.getReleaseYear(), currentState, borrowedUserId, reservedUserId));
    }
    return dtoList;
  }
  private void fireListChangedEvent() {
    // This ensures we don't hold a lock while notifying listeners
    for (ClientHandler client : server.getClients()) {
      Response response = new Response("success", "Vinyl state updated", getVinylListDTO());  // Send updated list
      client.send(JsonUtil.toJson(response));  // Send the updated response to the client
    }
    support.firePropertyChange("listChanged", null, vinylList);
  }

  public void replaceVinylList(List<Vinyl> newVinyls) {
    this.vinylList.clear();
    this.vinylList.addAll(newVinyls);
    support.firePropertyChange("listChanged", null, List.copyOf(vinylList));
  }


  @Override public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
    support.firePropertyChange(new PropertyChangeEvent(this, null, null, vinylList));
    // To tell ViewModel that the list has changed
  }

  @Override public void addPropertyChangeListener(String name, PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name, listener);
    support.firePropertyChange(new PropertyChangeEvent(this, name, null, vinylList));
  }

  @Override public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  @Override public void removePropertyChangeListener(String name, PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(name, listener);
  }
}
