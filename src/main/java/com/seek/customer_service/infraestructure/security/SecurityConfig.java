package com.seek.customer_service.infraestructure.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/public/**").permitAll()
                        .anyExchange().authenticated()
                )
                .authenticationManager(authenticationManager())
                .securityContextRepository(new JwtSecurityContextRepository(authenticationManager()))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(unauthorizedHandler()))
                .build();
    }

    @Bean
    public ServerAuthenticationEntryPoint unauthorizedHandler() {
        return (exchange, ex) -> {
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("error", "Unauthorized");
            response.put("message", ex.getMessage());
            response.put("status", HttpStatus.UNAUTHORIZED.value());

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            byte[] bytes;
            try {
                bytes = mapper.writeValueAsBytes(response);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().add("Content-Type", "application/json");
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                    .bufferFactory().wrap(bytes)));
        };
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return authentication -> {
            String token = authentication.getCredentials().toString();
            try {
                Claims claims = jwtUtil.getClaims(token);
                return Mono.just(
                        new UsernamePasswordAuthenticationToken(
                                claims.getSubject(),
                                token,
                                Collections.emptyList()
                        )
                );
            } catch (Exception e) {
                return Mono.error(new BadCredentialsException("Invalid token"));
            }
        };
    }
}
