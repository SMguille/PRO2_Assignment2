package Network;

import Util.JsonUtil;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.function.Consumer;

public class NetworkClient {
  private final String host;
  private final int port;

  private Socket socket;
  private BufferedReader reader;
  private PrintWriter writer;

  // Callback to deliver full-list updates (as VinylDTOs)
  private Consumer<List<VinylDTO>> onFullListReceived;

  public NetworkClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  /** Call this once at app startup to connect and begin listening. */
  public void start() throws IOException {
    socket = new Socket(host, port);
    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    writer = new PrintWriter(socket.getOutputStream(), true);

    // Spin up the listener thread
    new Thread(this::listenLoop, "NetworkClient-Listener").start();
  }

  /** Sends a Request (borrow, reserve, etc.) to the server. */
  public void sendRequest(Request request) {
    String json = JsonUtil.toJson(request);
    writer.println(json);
  }

  /** Register a callback to receive the full vinyl list whenever the server broadcasts it. */
  public void setOnFullListReceived(Consumer<List<VinylDTO>> callback) {
    this.onFullListReceived = callback;
  }

  /** Main loop: read lines, parse Responses, and dispatch full-list updates. */
  private void listenLoop() {
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        Response resp = JsonUtil.fromJson(line, Response.class);

        // Only invoke callback when we have a full vinylList payload
        if ("success".equals(resp.getStatus()) && resp.getVinylList() != null) {
          List<VinylDTO> dtos = resp.getVinylList();
          if (onFullListReceived != null) {
            // Must run on JavaFX thread to modify ObservableList
            Platform.runLater(() -> onFullListReceived.accept(dtos));
          }
        } else {
          // You can log other messages here if you like:
          System.out.println("Server: " + resp.getMessage());
        }
      }
      System.out.println("Server closed connection.");
    } catch (IOException e) {
      System.err.println("NetworkClient listen error: " + e.getMessage());
    } finally {
      close();
    }
  }

  /** Clean up resources on disconnect or error. */
  private void close() {
    try {
      if (socket != null) socket.close();
    } catch (IOException ignored) {}
  }
}
