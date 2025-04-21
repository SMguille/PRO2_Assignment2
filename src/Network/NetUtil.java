package Network;

import java.net.*;
import java.util.Enumeration;

public class NetUtil
{
  public static String findLocalIp() throws SocketException {
    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    while (interfaces.hasMoreElements()) {
      NetworkInterface ni = interfaces.nextElement();
      if (!ni.isUp() || ni.isLoopback()) continue;
      for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
        String ip = ia.getAddress().getHostAddress();
        // pick only IPv4
        if (ip.indexOf(':') < 0) return ip;
      }
    }
    return "127.0.0.1";
  }

}
