package Network;

import Model.Model;

public class RemoveRequestStrategy implements RequestStrategy {

  @Override
  public Response handle(Request request, Model model) {
    try {
      model.removeVinyl(request.getVinyl().toModel(), request.getUserId());
      return new Response("success", "Vinyl removed successfully.");
    } catch (Exception e) {
      return new Response("error", "Failed to remove vinyl: " + e.getMessage());
    }
  }
}
