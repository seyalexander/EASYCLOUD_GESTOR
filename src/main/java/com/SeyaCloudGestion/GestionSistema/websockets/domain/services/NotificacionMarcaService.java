package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionMarcaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionMarca;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionMarcaService implements INotificacionMarca {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionMarcaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionMarca_Registro(NotificacionMarcaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/marca/marca-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMarca_Edicion(NotificacionMarcaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/marca/marca-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMarca_Anular(NotificacionMarcaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/marca/marca-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMarca_Activar(NotificacionMarcaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/marca/marca-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
