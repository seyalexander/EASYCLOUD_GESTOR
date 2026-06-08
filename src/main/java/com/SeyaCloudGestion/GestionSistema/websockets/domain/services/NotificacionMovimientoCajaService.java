package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionMovimientoCajaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionMovimientoCaja;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionMovimientoCajaService implements INotificacionMovimientoCaja {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionMovimientoCajaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionMovimientoCaja_Registro(NotificacionMovimientoCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/movimientoCaja/movimientoCaja-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMovimientoCaja_Edicion(NotificacionMovimientoCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/movimientoCaja/movimientoCaja-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMovimientoCaja_Anular(NotificacionMovimientoCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/movimientoCaja/movimientoCaja-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMovimientoCaja_Activar(NotificacionMovimientoCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/movimientoCaja/movimientoCaja-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
