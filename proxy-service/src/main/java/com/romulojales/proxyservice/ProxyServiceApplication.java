package com.romulojales.proxyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.romulojales.prime.PrimeClient;
import com.romulojales.prime.PrimeService;

@SpringBootApplication(scanBasePackages = "com.romulojales.prime")
public class ProxyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyServiceApplication.class, args);
	}

	@Bean
	public PrimeService getPrimeService(PrimeClient primeClient) {
		return new PrimeService(primeClient);
	}

	@Bean
	public PrimeClient getPrimeClient() {
		return new PrimeClient();
	}
}
