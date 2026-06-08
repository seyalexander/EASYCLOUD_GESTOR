package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionNotaCreditoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionNotaCredito;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionNotaCreditoService implements INotificacionNotaCredito {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionNotaCreditoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionNotaCredito_Registro(NotificacionNotaCreditoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/notaCredito/notaCredito-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionNotaCredito_Edicion(NotificacionNotaCreditoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/notaCredito/notaCredito-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionNotaCredito_Anular(NotificacionNotaCreditoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/notaCredito/notaCredito-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionNotaCredito_Activar(NotificacionNotaCreditoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/notaCredito/notaCredito-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
