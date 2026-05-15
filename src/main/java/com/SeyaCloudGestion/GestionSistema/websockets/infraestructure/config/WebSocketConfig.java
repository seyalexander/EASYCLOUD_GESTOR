package com.SeyaCloudGestion.GestionSistema.websockets.infraestructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Prefijo para los destinos de suscripción (donde los clientes escuchan)
        config.enableSimpleBroker("/topic", "/queue");
        // Prefijo para los destinos de envío desde el cliente
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint principal para conexión WebSocket
        registry.addEndpoint("/api/v1/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();

        // Endpoint sin SockJS para clientes nativos WebSocket
        registry.addEndpoint("/api/v1/ws")
                .setAllowedOriginPatterns("*");
    }
}
