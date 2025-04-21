package Network;

import Model.Model;
import Util.JsonUtil;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
  private Socket socket;
  private Server server;
  private Model model;
  private BufferedReader reader;
  private PrintWriter writer;
  private Map<String, RequestStrategy> strategies;

  public ClientHandler(Socket socket, Server server, Model model) {
    this.socket = socket;
    this.server = server;
    this.model = model;
    this.strategies = initializeStrategies();

    try {
      this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.writer = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException e) {
      throw new RuntimeException("Error initializing client streams", e);
    }

    Logger.getInstance().log("NetworkClient connected: " + socket.getInetAddress() + ":" + socket.getPort(), Logger.green());
  }

  private Map<String, RequestStrategy> initializeStrategies() {
    Map<String, RequestStrategy> map = new HashMap<>();
    map.put("borrow", new BorrowRequestStrategy());
    map.put("reserve", new ReserveRequestStrategy());
    map.put("unreserve", new ReserveRequestStrategy());
    map.put("return", new ReturnRequestStrategy());
    map.put("remove", new RemoveRequestStrategy());
    map.put("addVinyl", new AddStrategy());
    return map;
  }

  @Override
  public void run() {
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        processRequest(line);
      }
    } catch (IOException e) {
      System.err.println("NetworkClient disconnected unexpectedly: " + e.getMessage());
    } finally {
      closeConnection();
    }
  }

  private void processRequest(String jsonLine) {
    try {
      Request request = JsonUtil.fromJson(jsonLine, Request.class);

      if (request == null || request.getType() == null || request.getType().isEmpty()) {
        sendError("Invalid request: Type is null or empty");
        return;
      }

      Logger.getInstance().log("Received request: " + request.getType(), Logger.yellow());

      RequestStrategy strategy = strategies.get(request.getType());
      if (strategy == null) {
        sendError("Unknown request type: " + request.getType());
        return;
      }

      Response operationResult = strategy.handle(request, model);

      // Send the operation result back to the sender
      send(JsonUtil.toJson(operationResult));

        Response fullUpdate = new Response(
            "success",
            "Vinyl list updated",
            model.getVinylListDTO()
        );
        server.broadcast(fullUpdate);

    } catch (Exception e) {
      sendError("Failed to process request: " + e.getMessage());
    }
  }

  private boolean shouldBroadcastUpdate(String type) {
    // Only broadcast for meaningful changes
    return type.equals("borrow") ||
        type.equals("reserve") ||
        type.equals("unreserve") ||
        type.equals("return") ||
        type.equals("remove") ||
        type.equals("addVinyl");
  }

  public void send(String json) {
    writer.println(json);
  }

  private void sendError(String errorMessage) {
    Response errorResponse = new Response("error", errorMessage);
    send(JsonUtil.toJson(errorResponse));
  }

  private void closeConnection() {
    try {
      server.removeClient(this);
      if (socket != null) socket.close();
      Logger.getInstance().log("NetworkClient disconnected: " + socket.getInetAddress() + ":" + socket.getPort(), Logger.red());
    } catch (IOException e) {
      System.err.println("Error closing client connection: " + e.getMessage());
    }
  }
}
