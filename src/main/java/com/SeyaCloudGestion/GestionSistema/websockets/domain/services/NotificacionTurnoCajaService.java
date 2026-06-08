package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTurnoCajaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionTurnoCaja;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionTurnoCajaService implements INotificacionTurnoCaja {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionTurnoCajaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionTurnoCaja_Registro(NotificacionTurnoCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/turnoCaja/turnoCaja-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTurnoCaja_Edicion(NotificacionTurnoCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/turnoCaja/turnoCaja-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTurnoCaja_Anular(NotificacionTurnoCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/turnoCaja/turnoCaja-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTurnoCaja_Activar(NotificacionTurnoCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/turnoCaja/turnoCaja-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
