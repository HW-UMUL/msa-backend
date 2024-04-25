package com.ssg.kms.security;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);

//        List<String> allowedOrigins = Arrays.asList("http://43.201.35.20/","https://umul.site/","http://localhost:5173", "http://183.109.102.242:11215/");
      List<String> allowedOrigins = Arrays.asList("http://43.201.35.20", "http://43.201.35.20/", "http://43.201.35.20:80/", "http://43.201.35.20:80", "http://43.201.35.20:8080/", "http://43.201.35.20:8080", "http://43.201.35.20:8080/","https://umul.site/", "https://umul.site", "http://umul.site/", "http://umul.site","http://localhost:5173", "http://183.109.102.242:11215/");
//        List<String> allowedOrigins = Arrays.asList("*");
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("content-type","Authorization", "Authorization-refresh", "Cache-Control", "Content-Type", "*"));
//        config.setExposedHeaders(List.of("Authorization", "Authorization-refresh"));
        List<String> exposedHeaders = new ArrayList<>();
        exposedHeaders.add("Authorization");
        exposedHeaders.add("Authorization-refresh");
        exposedHeaders.add("Access-Control-Allow-Origin");
        exposedHeaders.add("Access-Control-Allow-Credentials");
        config.setExposedHeaders(exposedHeaders);
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Authorization-refresh");
        config.addExposedHeader("Access-Control-Allow-Origin");
        config.addExposedHeader("Access-Control-Allow-Credentials");
//        config.setExposedHeaders(List.of("Authorization", "Authorization-refresh"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}