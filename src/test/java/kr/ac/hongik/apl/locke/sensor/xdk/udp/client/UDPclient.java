package kr.ac.hongik.apl.locke.sensor.xdk.udp.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;

@Component
public class UDPclient {
    // test client

    private String str;
//    private BufferedReader file; // Usage : Sending when I want
    private int serverPort;
    private int CLIENT_PORT = 2000; // target port

    private InetAddress serverAddress; // IP Address Wrapper
    private DatagramSocket datagramSocket; // Port socket

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

//    @Scheduled(fixedRate = 1000)
    public void sendJsonData() {
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

        UDPclient client = new UDPclient("192.168.0.72", 3500); // Mockup port 2000
        while (true) {

            client.sendJsonData();
            Thread.sleep(1000);
        }
//        Thread.sleep(5000);
    }
}
