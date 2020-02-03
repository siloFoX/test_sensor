package kr.ac.hongik.apl.locke.sensor.xdk.udp.server;

import java.net.*;
import java.io.*;


import kr.ac.hongik.apl.locke.sensor.xdk.kafka.controller.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UDPserver implements InitializingBean, DisposableBean { // todo : Make it Spring

    KafkaProducer kafkaProducer = new KafkaProducer();

    // todo : Map 으로 Bean 만들고 Autowired 하자

    @Autowired(required = false)
    public UDPserver (int port) {

        try {

            DatagramSocket datagramSocket = new DatagramSocket(port); // Mapping socket with port

            while (true) {

                byte buffer[] = new byte[512];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length); // Mapping buffer with packet

                System.out.println("ready");
                datagramSocket.receive(datagramPacket); // Wait until receive packet

                String str = new String(datagramPacket.getData()); // Extract String data from byte packet
                kafkaProducer.setAndSendRecentSensorData(str);

                // todo : T1 : mv to DB

                System.out.println("Data send success");
                datagramSocket.send(datagramPacket); // Send HeartBeat

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new UDPserver(3500);
    }

    // todo : Actuator 필요함
    @Override
    public void afterPropertiesSet() throws Exception {
        // todo : 여기서 실행 하니까 startService 만들기
        System.out.println("hello");
    }

    @Override
    public void destroy() throws Exception {
        // todo : 파괴될 때 DB 세팅 끝내고 죽게
    }
}
