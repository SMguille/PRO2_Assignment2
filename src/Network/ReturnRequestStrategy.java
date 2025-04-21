package Network;

import Model.Model;

public class ReturnRequestStrategy implements RequestStrategy
{

  @Override public Response handle(Request request, Model model)
  {
    try
    {
      model.returnVinyl(request.getVinyl().toModel(), request.getUserId());
      return new Response("success", "Vinyl returned successfully.");
    }
    catch (Exception e)
    {
      return new Response("error", "Failed to return vinyl: " + e.getMessage());
    }
  }

}