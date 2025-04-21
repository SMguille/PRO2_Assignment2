package Network;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.HttpURLConnection;

public class Logger {

  private static Logger instance;
  private static final Object lock = new Object(); // for thread-safety
  private final String publicIp;
  private final String privateIp;

  // ANSI color codes
  private static final String RESET = "\u001B[0m";
  private static final String GREEN = "\u001B[32m";
  private static final String YELLOW = "\u001B[33m";
  private static final String RED = "\u001B[31m";

  private Logger() {
    this.publicIp = fetchPublicIp();
    this.privateIp = fetchPrivateIp();
  }

  public static Logger getInstance() {
    if (instance == null) {
      synchronized (lock) {
        if (instance == null) {
          instance = new Logger();
        }
      }
    }
    return instance;
  }

  private String fetchPublicIp() {
    try {
      URL url = new URL("https://api.ipify.org");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(3000);
      connection.setReadTimeout(3000);

      try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
        StringBuilder response = new StringBuilder();
        int data;
        while ((data = reader.read()) != -1) {
          response.append((char) data);
        }
        return response.toString();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return "UnknownPublicIP";
    }
  }

  private String fetchPrivateIp() {
    try {
      InetAddress localHost = InetAddress.getLocalHost();
      return localHost.getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
      return "UnknownPrivateIP";
    }
  }

  // Standard log (no special color)
  public void log(String message) {
    log(message, RESET);
  }

  // Colored log
  public void log(String message, String color) {
    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    String logEntry = String.format("[%s] [Public:%s] [Private:%s] %s", timestamp, publicIp, privateIp, message);

    // Print with color
    System.out.println(color + logEntry + RESET);

    // Save in file without colors
    String fileName = "logs/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
    new File("logs").mkdirs(); // Create logs folder if it doesn't exist

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
      writer.write(logEntry);
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Expose color constants if needed elsewhere
  public static String green() { return GREEN; }
  public static String yellow() { return YELLOW; }
  public static String red() { return RED; }
}
