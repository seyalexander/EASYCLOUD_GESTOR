package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSotckDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionSotck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionSotckService implements INotificacionSotck {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionSotckService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionSotck_Registro(NotificacionSotckDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/sotck/sotck-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSotck_Edicion(NotificacionSotckDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/sotck/sotck-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSotck_Anular(NotificacionSotckDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/sotck/sotck-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSotck_Activar(NotificacionSotckDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/sotck/sotck-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
