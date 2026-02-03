package org.mg.urlshort.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
public class KeyGeneratorConfig {
    /*
    TODO Probably not the best idea for higher traffic
     */
    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}
