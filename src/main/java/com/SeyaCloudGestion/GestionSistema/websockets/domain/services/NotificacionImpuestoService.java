package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionImpuestoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionImpuesto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionImpuestoService implements INotificacionImpuesto {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionImpuestoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionImpuesto_Registro(NotificacionImpuestoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/impuesto/impuesto-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionImpuesto_Edicion(NotificacionImpuestoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/impuesto/impuesto-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionImpuesto_Anular(NotificacionImpuestoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/impuesto/impuesto-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionImpuesto_Activar(NotificacionImpuestoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/impuesto/impuesto-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
