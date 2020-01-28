package kr.ac.hongik.apl.locke.sensor.xdk.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j; // log for java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Setter
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    private static Map mappedSensor;

    private static final String TOPIC = "Lee1";

//    List<Object> sensorData = new ArrayList<>();
//    List<Object> sensorBuffer = new ArrayList<>();
//    int size=0;
//    int timeOut=0;
//    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yy:MM:dd");

    // 1초마다 데이터를 Kafka 에 저장 및 로컬 버퍼에 저장 todo : 살리기
//    @Scheduled(fixedRate = 1000)
//    public void autoSend(){
//
//        size = sensorData.size();
//        if(size == 0){ // 데이터가 생성된게 없으면,
//            if(++timeOut > 5){ // 데이터가 생성 안 된지 기준치 초과일 때, ( timeout 5 seconds )
//                System.out.println("Check the sensor");
//            }else{
//                System.out.println("There is no new data");
//            }
//
//        }else{ // 데이터가 생성되었다면,
//
//            timeOut = 0;
//            for(int i = 0; i < size; ++i){
//
//                kafkaTemplate.send("Lee1", sensorData.get(i));
//                System.out.println(String.format("data %d : %s produced successfully", i, sensorData.get(i)));
//                sensorBuffer.add(sensorData.get(i)); // 임시 버퍼에서 로컬 버퍼로 복사
//            }
//            sensorData.clear(); // 임시 버퍼 초기화
//        }
//
//    }

    public void setRecentSensorData (String recentSensorData) {
        // Get sensor data from UDP server and store it to temporary buffer(called sensorData)

        try {

            mappedSensor = objectMapper.readValue(recentSensorData, new TypeReference<Map<String, String>>() {}); // Preprocess : Json to Map
//            sensorData.add(mappedSensor);
            inputDateTime();
            kafkaTemplate.send(TOPIC, mappedSensor); // Directly send
            System.out.println("kafka Send success : { topic : " + TOPIC + " data : " + mappedSensor.toString() + " }");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void inputDateTime () {
        // Input date and time in sensor data
        // todo : If XDK send time data, This func will be removed
        // todo : warning - uses unchecked or unsafe operations.

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        mappedSensor.put("time", zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss z" )));
    }
}