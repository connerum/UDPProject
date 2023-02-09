package org.example;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class UDPTimeClient {
    private DatagramSocket socket;
    private byte[] buf = new byte[1024];

    public UDPTimeClient() throws SocketException {
        socket = new DatagramSocket();
    }

    public static void main(String[] args) {
        try {
            InetAddress serverIP = InetAddress.getByName("129.6.15.28");
            int serverPort = 37;

            UDPTimeClient client = new UDPTimeClient();
            client.service(serverIP, serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.out.println("Could not resolve hostname.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void service(InetAddress serverIP, int serverPort) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, serverIP, serverPort);
        //SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy h:mm,a", Locale.ENGLISH);

        try {
            socket.send(sendPacket);

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);


            byte[] timeStamp = receivePacket.getData();
            System.out.println("Reply from server: " + timeStamp.hashCode());

            //DatagramPacket request = new DatagramPacket(new byte[1024], 32);
            //socket.receive(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
