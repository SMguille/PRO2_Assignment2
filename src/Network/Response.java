package Network;

import java.util.List;

public class Response {
  private String status; // "success" or "error"
  private String message;
  private List<VinylDTO> vinylList;

  // Default constructor
  public Response() {}

  // Constructor for success responses with vinyl list
  public Response(String status, String message, List<VinylDTO> vinylList) {
    this.status = status;
    this.message = message;
    this.vinylList = vinylList;
  }

  // Constructor for error or success responses without vinyl list
  public Response(String status, String message) {
    this.status = status;
    this.message = message;
    this.vinylList = null;  // No vinyl list in error responses
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public List<VinylDTO> getVinylList() {
    return vinylList;
  }
}
