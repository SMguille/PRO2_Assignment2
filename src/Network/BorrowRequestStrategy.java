package Network;

import Model.Model;

public class BorrowRequestStrategy implements RequestStrategy {

  @Override
  public Response handle(Request request, Model model) {
    try {
      model.borrowVinyl(request.getVinyl().toModel(), request.getUserId());
      // Create a success response
      return new Response("success", "Vinyl borrowed successfully.");
    } catch (Exception e) {
      // If an error occurs, return an error response
      return new Response("error", "Failed to borrow vinyl: " + e.getMessage());
    }
  }
}
