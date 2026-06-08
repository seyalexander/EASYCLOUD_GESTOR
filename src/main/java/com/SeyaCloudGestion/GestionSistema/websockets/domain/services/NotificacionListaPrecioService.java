package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionListaPrecioDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionListaPrecio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionListaPrecioService implements INotificacionListaPrecio {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionListaPrecioService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionListaPrecio_Registro(NotificacionListaPrecioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/listaPrecio/listaPrecio-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionListaPrecio_Edicion(NotificacionListaPrecioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/listaPrecio/listaPrecio-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionListaPrecio_Anular(NotificacionListaPrecioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/listaPrecio/listaPrecio-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionListaPrecio_Activar(NotificacionListaPrecioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/listaPrecio/listaPrecio-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
