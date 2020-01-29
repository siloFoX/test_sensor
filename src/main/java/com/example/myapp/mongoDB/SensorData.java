package com.example.myapp.mongoDB;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "keepData")
public class SensorData {
    private String date;
    private String time;
    private long ts;

    public SensorData(String date, String time, long ts) {
        this.date = date;
        this.time = time;
        this.ts = ts;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String tsString() {
        return "\"" + ts + "\"";
    }

    public String toJsonString(){
        String jsonSensor = "{\"date\":\""+this.date+"\",\"time\":\""+this.time+"\",\"ts\":"+this.tsString()+"}";
        return jsonSensor;
    }

    public Map toMap() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map objectSensor = objectMapper.readValue(toJsonString(), new TypeReference<Map<String,String>>() {});
        return objectSensor;
    }
}


