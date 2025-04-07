package com.apiNft.NFTAPI.infra.security.config;

import io.minio.MinioClient;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@ConfigurationProperties(prefix = "minio.api")
@Configuration
public class MinioClientConfig {

    private String uri;
    private String login;
    private String password;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(uri)
                .credentials(login, password)
                .build();
    }

}
