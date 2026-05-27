package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionComprobanteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionComprobante;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionComprobanteService implements INotificacionComprobante {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionComprobanteService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionComprobante_Registro(NotificacionComprobanteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/comprobante/comprobante-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionComprobante_Edicion(NotificacionComprobanteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/comprobante/comprobante-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionComprobante_Anular(NotificacionComprobanteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/comprobante/comprobante-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionComprobante_Activar(NotificacionComprobanteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/comprobante/comprobante-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
