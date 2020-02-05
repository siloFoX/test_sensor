package kr.ac.hongik.apl.locke.sensor.xdk.udp.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UDPserverConfiguration {

    int port = 3500;

    @Autowired
    public UDPserverConfiguration(int port) {

        this.port = port;
    }

    @Bean
    public int UDPserverPort () {

        return port;
    }

    public int getPort() {
        return port;
    }
}
