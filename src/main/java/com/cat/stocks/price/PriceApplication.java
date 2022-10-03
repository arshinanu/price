package com.cat.stocks.price;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceApplication.class, args);
	}

}
