package com.amin.backenddevelopertask;

import com.amin.backenddevelopertask.config.CloudinaryConfig;
import com.amin.backenddevelopertask.config.CloudinaryProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CloudinaryProperties.class)
public class BackendDeveloperTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendDeveloperTaskApplication.class, args);
    }

}
