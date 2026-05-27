package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionParametroDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionParametro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionParametroService implements INotificacionParametro {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionParametroService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionParametro_Registro(NotificacionParametroDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/parametro/parametro-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionParametro_Edicion(NotificacionParametroDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/parametro/parametro-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionParametro_Anular(NotificacionParametroDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/parametro/parametro-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionParametro_Activar(NotificacionParametroDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/parametro/parametro-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
