package com.peacefulotter.ffserver.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Collections;

@SpringBootApplication
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketApp implements WebSocketMessageBrokerConfigurer
{
    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(WebSocketApp.class);
        app.setDefaultProperties( Collections.singletonMap("server.port", "8888"));
        app.run(args);
    }

    @Override
    public void configureMessageBroker( MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints( StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000").withSockJS();
    }
}