package com.example.myapp.mongoDB;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClients;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RestController
public class ReceiveAck {

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value="/ack", method = RequestMethod.POST)
    @ResponseBody
    public void Receive(@RequestBody Map<String,Object> lastTs) {
        //ObjectMapper objectMapper = new ObjectMapper();
        //Map lastTs = objectMapper.readValue(jsonLast,  new TypeReference<Map<String,String>>() {});
        String timeStamp = (String)lastTs.get("ts");
        long ts = Long.parseLong(timeStamp);
        Query query = new Query(Criteria.where("ts").lte(ts));
        mongoTemplate.remove(query,"keepData");
        System.out.println("delete data");
    }
}
