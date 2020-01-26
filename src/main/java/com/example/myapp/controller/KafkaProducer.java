package com.example.myapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    List<Object> sensorData = new ArrayList<>();
    List<Object> sensorBuffer = new ArrayList<>();
    int size=0;
    int timeOut=0;
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yy:MM:dd");

    // 1초마다 데이터를 kafka에 저장 및 로컬 버퍼에 저장
    @Scheduled(fixedRate = 1000)
    public void autoSend(){
        size = sensorData.size();
        if(size == 0){ // 데이터가 생성된게 없으면,
            if(++timeOut > 5){ // 데이터가 생성 안 된지 기준치 초과일 떄,
                System.out.println("check the sensor");
            }else{
                System.out.println("there is no new data");
            }

        }else{ // 데이터가 생성되었다면,
            timeOut = 0;
            for(int i = 0;i < size;++i){
                kafkaTemplate.send("Lee1", sensorData.get(i));
                System.out.println(String.format("data %d : %s produced successfully",i, sensorData.get(i)));
                sensorBuffer.add(sensorData.get(i)); // 임시 버퍼에서 로컬 버퍼로 복사
            }
            sensorData.clear(); // 임시 버퍼 초기화
        }

    }

    // 1.1초마다 데이터 생성 후 임시 버퍼에 담기
    @Scheduled(fixedRate = 1100 )
    public void data() throws IOException {
        String time = timeFormat.format(new Date());
        String date = dateFormat.format(new Date());
        String jsonSensor = "{\"date\":\""+date+"\",\"time\":\""+time+"\"}";
        Map objectSensor = objectMapper.readValue(jsonSensor, new TypeReference<Map<String,String>>() {});
        sensorData.add(objectSensor);
    }

}