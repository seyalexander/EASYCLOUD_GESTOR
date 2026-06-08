package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionProductoPrecioDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionProductoPrecio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionProductoPrecioService implements INotificacionProductoPrecio {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionProductoPrecioService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionProductoPrecio_Registro(NotificacionProductoPrecioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/productoPrecio/productoPrecio-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionProductoPrecio_Edicion(NotificacionProductoPrecioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/productoPrecio/productoPrecio-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionProductoPrecio_Anular(NotificacionProductoPrecioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/productoPrecio/productoPrecio-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionProductoPrecio_Activar(NotificacionProductoPrecioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/productoPrecio/productoPrecio-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
