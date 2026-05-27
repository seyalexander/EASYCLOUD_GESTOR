package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionKardexDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionKardex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionKardexService implements INotificacionKardex {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionKardexService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionKardex_Registro(NotificacionKardexDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/kardex/kardex-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionKardex_Edicion(NotificacionKardexDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/kardex/kardex-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionKardex_Anular(NotificacionKardexDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/kardex/kardex-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionKardex_Activar(NotificacionKardexDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/kardex/kardex-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
