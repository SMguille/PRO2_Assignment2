package Network;

import Model.Model;
import Util.JsonUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static Network.NetUtil.findLocalIp;

public class Server {
  private static final int PORT = 8000;
  private Model model;
  private List<ClientHandler> clients;

  public Server(Model model) {
    this.model = model;
    this.clients = new CopyOnWriteArrayList<>();
  }

  public void start() {
    String host;
    try {
      host = findLocalIp();
      System.out.printf("Server will listen on LAN IP %s:%d (and all other interfaces)%n", host, PORT);
    } catch (SocketException e) {
      System.err.println("Could not auto‑detect LAN IP, defaulting to all interfaces only.");
      host = "0.0.0.0";
    }

    // Bind to all interfaces on PORT:
    try (ServerSocket serverSocket = new ServerSocket(PORT, 50)) {
      System.out.println("Server started. Awaiting client connections...");

      while (true) {
        Socket clientSocket = serverSocket.accept();
        ClientHandler handler = new ClientHandler(clientSocket, this, model);
        clients.add(handler);
        new Thread(handler).start();
      }
    } catch (IOException e) {
      System.err.println("Server error on port " + PORT + ": " + e.getMessage());
    }
  }

  public void broadcast(Response response) {
    String responseJson = JsonUtil.toJson(response);

    for (ClientHandler client : clients) {
      try {
        client.send(responseJson);
      } catch (Exception e) {
        System.err.println("Removing client due to send failure: " + e.getMessage());
        removeClient(client);
      }
    }
  }

  public void removeClient(ClientHandler clientHandler) {
    clients.remove(clientHandler);
  }
  public List<ClientHandler> getClients(){
    return clients;
  }
}
