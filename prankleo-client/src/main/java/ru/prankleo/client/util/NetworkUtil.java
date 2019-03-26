package ru.prankleo.client.util;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkUtil {

    private NetworkUtil(){}

    public static String getCurrentIP() {
        String ip = "127.0.0.1";
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (Exception ex) {

        }

        return ip;
    }
}
