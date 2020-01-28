package com.example.myapp.udp.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.sql.CallableStatement;

@Slf4j
@Component
public class UDPclient {
    // test client
    // todo : Move to test dir

    private static String str;
//    private BufferedReader file; // Usage : Sending when I want
    private static int serverPort;
    private static final int CLIENT_PORT = 2000; // target port

    private static InetAddress serverAddress; // IP Address Wrapper
    private static DatagramSocket datagramSocket; // Port socket

    @Autowired (required = false) // Off the Bean require
    public UDPclient(String serverIp, int serverPort) {
        // constructor

        try {

            this.serverAddress = InetAddress.getByName(serverIp);
            this.datagramSocket = new DatagramSocket(CLIENT_PORT);
            this.serverPort = serverPort;
            this.str = "{" +
                    "\"Accel_X\": \"31\"," +
                    "\"Accel_Y\": \"29\",\n" +
                    "\"Accel_Z\": \"1037\",\n" +
                    "\"Mag_X\": \"7\",\n" +
                    "\"Mag_Y\": \"10\",\n" +
                    "\"Mag_Z\": \"-43\",\n" +
                    "\"Mag_R\": \"6476\",\n" +
                    "\"Gyro_X\": \"6347\",\n" +
                    "\"Gyro_Y\": \"3662\",\n" +
                    "\"Gyro_Z\": \"4516\",\n" +
                    "\"RH\": \"52\",\n" +
                    "\"Pressure\": \"100388\",\n" +
                    "\"Light\": \"446400\",\n" +
//                    "\"Noise\": \"0.00229065\",\n" +
                    "\"Temp\": \"21591\"" +
                    "}";

        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }
    public static void sendJsonData() {
        // Send mock data every 1.1 second

        try {

            System.out.print("message : ");

//        If I press enter, then data is sent
//        file = new BufferedReader(new InputStreamReader(System.in));
//        str = file.readLine();

            byte buffer[] = str.getBytes();

            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            datagramSocket.send(datagramPacket);

            buffer = new byte[512];
            datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(datagramPacket);

            System.out.println("server ip : " + datagramPacket.getAddress() + " , server port : " + datagramPacket.getPort());
            System.out.println("data : " + new String(datagramPacket.getData()).trim());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException, InterruptedException {

        new UDPclient("192.168.0.72", 3500); // Mockup port 2000
        while (true) {

            UDPclient.sendJsonData();
            Thread.sleep(1000);
        }
    }
}
