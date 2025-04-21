package Network;

import Model.Model;
import Model.Vinyl;

public class AddStrategy implements RequestStrategy {

  @Override
  public Response handle(Request request, Model model) {
    try {
      Vinyl vinyl = request.getVinyl().toModel();
      model.addVinyl(vinyl.getTitle(), vinyl.getArtist(), vinyl.getReleaseYear());
      // Create a success response
      return new Response("success", "Vinyl added successfully.");
    } catch (Exception e) {
      // If an error occurs, return an error response
      return new Response("error", "Failed to added vinyl: " + e.getMessage());
    }
  }
}
