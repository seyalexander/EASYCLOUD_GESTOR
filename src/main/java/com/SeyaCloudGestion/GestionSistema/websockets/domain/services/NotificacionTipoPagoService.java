package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoPagoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionTipoPago;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionTipoPagoService implements INotificacionTipoPago {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionTipoPagoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionTipoPago_Registro(NotificacionTipoPagoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoPago/tipoPago-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoPago_Edicion(NotificacionTipoPagoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoPago/tipoPago-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoPago_Anular(NotificacionTipoPagoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoPago/tipoPago-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoPago_Activar(NotificacionTipoPagoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoPago/tipoPago-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
