package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionAjusteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionAjuste;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionAjusteService implements INotificacionAjuste {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionAjusteService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionAjuste_Registro(NotificacionAjusteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/ajuste/ajuste-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionAjuste_Edicion(NotificacionAjusteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/ajuste/ajuste-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionAjuste_Anular(NotificacionAjusteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/ajuste/ajuste-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionAjuste_Activar(NotificacionAjusteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/ajuste/ajuste-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
