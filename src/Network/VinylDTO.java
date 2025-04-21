package Network;

import Model.*;

public class VinylDTO {
  private String title;
  private String artist;
  private int releaseYear;
  private String currentState;
  private String borrowedUserId;
  private String reservedUserId;

  public VinylDTO() {
    // Empty constructor for Gson
  }
  public VinylDTO(String title, String artist, int releaseYear,
      String currentState, String borrowedUserId, String reservedUserId)
  {
    this.title = title;
    this.artist = artist;
    this.releaseYear = releaseYear;
    this.currentState = currentState;
    this.borrowedUserId = borrowedUserId;
    this.reservedUserId = reservedUserId;
  }


  public static VinylDTO fromModel(Vinyl v) {
    VinylDTO dto = new VinylDTO();
    dto.title = v.getTitle();
    dto.artist = v.getArtist();
    dto.releaseYear = v.getReleaseYear();
    dto.currentState = v.getCurrentState().getClass().getSimpleName();
    dto.borrowedUserId = v.getBorrowedUserId();
    dto.reservedUserId = v.getReservedUserId();
    return dto;
  }

  public Vinyl toModel() {
    Vinyl vinyl = new Vinyl(title, artist, releaseYear, borrowedUserId, reservedUserId);

      switch (currentState) {
        case "ReservedState" -> vinyl.setCurrentState(new ReservedState());
        case "BorrowedState" -> vinyl.setCurrentState(new BorrowedState());
        case "BorrowedReservedState" -> vinyl.setCurrentState(new BorrowedReservedState());
        default -> vinyl.setCurrentState(new AvailableState());
      }
    return vinyl;
  }

  // Getters and setters (you need them for Gson!)

  public String getTitle() {
    return title;
  }
  public String getArtist() {
    return artist;
  }
  public int getReleaseYear() {
    return releaseYear;
  }
  public String getCurrentState() {
    return currentState;
  }
  public String getBorrowedUserId() {
    return borrowedUserId;
  }
  public String getReservedUserId() {
    return reservedUserId;
  }

  public void setTitle(String title) {
    this.title = title;
  }
  public void setArtist(String artist) {
    this.artist = artist;
  }
  public void setReleaseYear(int releaseYear) {
    this.releaseYear = releaseYear;
  }
  public void setCurrentState(String currentState) {
    this.currentState = currentState;
  }
  public void setBorrowedUserId(String borrowedUserId) {
    this.borrowedUserId = borrowedUserId;
  }
  public void setReservedUserId(String reservedUserId) {
    this.reservedUserId = reservedUserId;
  }
}
