package Network;

import Model.Model;

public interface RequestStrategy {
  Response handle(Request request, Model model);
}
