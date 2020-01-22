package com.example.myapp.udp.client;

import java.net.*;
import java.io.*;

public class UDPclient {

    // test client

    private String str;
    private BufferedReader file;
    private static int SERVER_PORT = 3500;

    public UDPclient(String ip, int port) {

        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            DatagramSocket datagramSocket = new DatagramSocket(port);

            for(int i = 0; i < 10; i++) {

                System.out.print("message : ");

                file = new BufferedReader(new InputStreamReader(System.in));
                str = file.readLine();
                str = "{" +
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
                        "\"Noise\": \"0.00229065\",\n" +
                        "\"Temp\": \"21591\"" +
                        "}";



                byte buffer[] = str.getBytes();

                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, SERVER_PORT);
                datagramSocket.send(datagramPacket);

                buffer = new byte[512];
                datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);

                System.out.println("server ip : " + datagramPacket.getAddress() + " , server port : " + datagramPacket.getPort());
                System.out.println("data : " + new String(datagramPacket.getData()).trim());
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UDPclient("192.168.0.72", 2000);
    }
}
