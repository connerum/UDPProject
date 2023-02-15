package org.example;

import java.net.*;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.text.SimpleDateFormat;

public class UDPTimeClient {
    private DatagramSocket socket;

    public UDPTimeClient() throws SocketException {
        socket = new DatagramSocket();
    }

    public static void main(String[] args) {
        try {
            InetAddress serverIP = InetAddress.getByName("129.6.15.28");
            int port = 37;
            UDPTimeClient client = new UDPTimeClient();
            client.service(serverIP, port);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.out.println("Could not resolve the hostname.");
        }
    }

    public void service(InetAddress address, int port) {
        byte[] buffer = new byte[0];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, port);

        try {
            socket.send(request);

            buffer = new byte[4];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            System.out.println(timeFormatter(buffer));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String timeFormatter(byte[] buffer) {
        long secondsSince1900 = 0;
        for (int i = 0; i < 4; i++) {
            secondsSince1900 = (secondsSince1900 << 8) | (buffer[i] & 0xff);
        }
        long secondsSince1970 = secondsSince1900 - 2208988800L;
        Date date = new Date(secondsSince1970 * 1000);

        Calendar calendar = Calendar.getInstance();
        SimpleTimeZone timeZone = new SimpleTimeZone(0, "UTC");
        calendar.setTimeZone(timeZone);
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }
}
