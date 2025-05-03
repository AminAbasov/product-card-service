package com.amin.backenddevelopertask.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
@Configuration
@EnableConfigurationProperties(CloudinaryProperties.class)
public class CloudinaryConfig {

    private final CloudinaryProperties properties;

    public CloudinaryConfig(CloudinaryProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Cloudinary cloudinary() {
        Map<String, Object> config = ObjectUtils.asMap(
                "cloud_name", properties.getCloudName(),
                "api_key", properties.getApiKey(),
                "api_secret", properties.getApiSecret(),
                "secure", true
        );
        return new Cloudinary(config);
    }
}

