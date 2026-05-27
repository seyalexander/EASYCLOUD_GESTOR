package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionDevolucionDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionDevolucion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionDevolucionService implements INotificacionDevolucion {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionDevolucionService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionDevolucion_Registro(NotificacionDevolucionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/devolucion/devolucion-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionDevolucion_Edicion(NotificacionDevolucionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/devolucion/devolucion-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionDevolucion_Anular(NotificacionDevolucionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/devolucion/devolucion-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionDevolucion_Activar(NotificacionDevolucionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/devolucion/devolucion-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
