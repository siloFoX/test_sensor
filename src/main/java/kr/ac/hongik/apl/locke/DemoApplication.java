package kr.ac.hongik.apl.locke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {
	// todo : Make thread class
	// todo : Install Kafka at SmartProcess server

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
