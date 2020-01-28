package com.example.myapp.udp.server;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.myapp.controller.KafkaProducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UDPserver { // todo : Comment, Make it spring

    KafkaProducer kafkaProducer = new KafkaProducer();

    @Autowired(required = false)
    public UDPserver (int port) {

        try {

            DatagramSocket datagramSocket = new DatagramSocket(port);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map;

            while (true) {

                byte buffer[] = new byte[512];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

                System.out.println("ready");
                datagramSocket.receive(datagramPacket);

                String str = new String(datagramPacket.getData());
                kafkaProducer.setRecentSensorData(str);

                // todo : T1 : mv to DB

                System.out.println("Data send success : " + str.substring(0, 10) + " ...");
                datagramSocket.send(datagramPacket);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new UDPserver(3500);
    }
}
