package com.apiNft.NFTAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NftApiApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(NftApiApplication.class);
		springApplication.setAdditionalProfiles("dev");
		springApplication.run();
	}
}


