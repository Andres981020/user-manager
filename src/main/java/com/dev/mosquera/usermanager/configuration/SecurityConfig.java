package com.dev.mosquera.usermanager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**")
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/users/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .httpBasic((Customizer.withDefaults()))
                .build();
    }
}
