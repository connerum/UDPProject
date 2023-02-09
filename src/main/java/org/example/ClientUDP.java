package org.example;

import java.net.*;
import java.util.Scanner;

public class ClientUDP {
    private DatagramSocket socket;

    public  ClientUDP() throws SocketException {
        socket = new DatagramSocket();
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("syntax: ClientUDP <ServerIP> <ServerPort>");
            return;
        }

        try {
            InetAddress serverIP = InetAddress.getByName(args[0]);
            int serverPort = Integer.parseInt(args[1]);
            ClientUDP client = new ClientUDP();
            client.service(serverIP, serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.out.println("Could not resolve hostname.");
        }
    }

    public void service(InetAddress serverIP, int serverPort) {
        Scanner keyboard = new Scanner(System.in);
        String message = keyboard.nextLine();

        byte[] buffer = message.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, serverIP, serverPort);

        try {
            socket.send(sendPacket);
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            String reply = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Reply from server: " + reply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
