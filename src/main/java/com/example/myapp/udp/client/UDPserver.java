package com.example.myapp.udp.client;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UDPserver {

    public UDPserver (int port) {

        try {

            DatagramSocket datagramSocket = new DatagramSocket(port);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map;

            while (true) { // todo : T1 RUNNABLE

                byte buffer[] = new byte[512];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

                System.out.println("ready");
                datagramSocket.receive(datagramPacket);

                String str = new String(datagramPacket.getData());
                map = objectMapper.readValue(str, new TypeReference<Map<String, String>>(){});


                // todo : Kafka Producer
                // todo : T1 : mv to DB

                System.out.println(" : " + map.get("RH"));
                datagramSocket.send(datagramPacket);
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new UDPserver(3500);
    }
}
