package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSerieCajaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionCierreCaja;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionCierreCajaService implements INotificacionCierreCaja {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionCierreCajaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionCierreCaja_Registro(NotificacionSerieCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cierreCaja/cierreCaja-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCierreCaja_Edicion(NotificacionSerieCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cierreCaja/cierreCaja-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCierreCaja_Anular(NotificacionSerieCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cierreCaja/cierreCaja-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCierreCaja_Activar(NotificacionSerieCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cierreCaja/cierreCaja-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
