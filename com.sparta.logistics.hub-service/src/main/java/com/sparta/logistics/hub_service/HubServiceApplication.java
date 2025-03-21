package com.sparta.logistics.hub_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class HubServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HubServiceApplication.class, args);

	}

}
