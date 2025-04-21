package Network;

public class Request {
  private String type;     // Instead of "action", we now call it "type" (borrow, reserve, return, etc.)
  private String userId;
  private VinylDTO vinyl;

  public Request() {}

  public Request(String type, String userId, VinylDTO vinyl) {
    this.type = type;
    this.userId = userId;
    this.vinyl = vinyl;
  }

  public String getType() {
    return type;
  }

  public String getUserId() {
    return userId;
  }

  public VinylDTO getVinyl() {
    return vinyl;
  }
}
