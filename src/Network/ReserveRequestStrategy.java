package Network;

import Model.Model;
import Model.Vinyl;

public class ReserveRequestStrategy implements RequestStrategy {

  @Override
  public Response handle(Request request, Model model) {
    String action = request.getType(); // It will be either "reserve" or "unreserve"
    String userId = request.getUserId();
    VinylDTO vinylDTO = request.getVinyl();
    Vinyl vinyl = vinylDTO.toModel();

    if ("reserve".equals(action)) {
      model.reserveVinyl(vinyl, userId);
      return new Response("success", "Vinyl reserved successfully.");
    } else if ("unreserve".equals(action)) {
      model.reserveVinyl(vinyl, userId);
      return new Response("success", "Vinyl unreserved successfully.");
    } else {
      return new Response("error", "Failed to reserve/unreserve vinyl: " + action);
    }
  }
}
