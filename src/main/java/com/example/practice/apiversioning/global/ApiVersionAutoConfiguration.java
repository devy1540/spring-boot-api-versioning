package com.example.practice.apiversioning.global;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApiVersionProperties.class)
public class ApiVersionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApiVersionWebMVCRegistrations apiVersionWebMVCRegistrations(ApiVersionProperties apiVersionProperties) {
        return new ApiVersionWebMVCRegistrations(apiVersionProperties);
    }
}
