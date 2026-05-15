package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionArticuloDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionArticulos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionArticuloService implements INotificacionArticulos {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionArticuloService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    @Override
    public void enviarNotificacionArticulo_Registro(NotificacionArticuloDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/articulo/articulo-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionArticulo_Edicion(NotificacionArticuloDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/articulo/articulo-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionArticulo_Anular(NotificacionArticuloDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/articulo/articulo-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionArticulo_Activar(NotificacionArticuloDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/articulo/articulo-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
