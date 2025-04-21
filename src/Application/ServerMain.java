package Application;

import Model.Model;
import Network.Server;

public class ServerMain {
  public static void main(String[] args) {
    Model model = new Model(); // Create the shared Model
    Server server = new Server(model); // Create the Server with Model
    server.start(); // Start the Server (it will wait for clients)
  }
}
