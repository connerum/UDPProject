package org.example;

import java.io.IOError;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerUDP {
    private DatagramSocket socket;

    public ServerUDP(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public static void main(String[] args){
        if (args.length != 1) {
            System.out.println("syntax: ServerUDP <port_number>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        try {
            ServerUDP server = new ServerUDP(port);
            server.service();
        }catch (SocketException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void service() throws IOException {
        while (true) {
            DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
            socket.receive(request);
            byte[] message = request.getData();

            System.out.println(new String(message));

            InetAddress clientIP = request.getAddress();
            int clientPort = request.getPort();

            DatagramPacket reply = new DatagramPacket(message, message.length, clientIP, clientPort);

            socket.send(reply);
        }
    }
}
