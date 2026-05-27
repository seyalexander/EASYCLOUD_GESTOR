package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionPagoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionPago;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionPagoService implements INotificacionPago {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionPagoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionPago_Registro(NotificacionPagoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pago/pago-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPago_Edicion(NotificacionPagoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pago/pago-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPago_Anular(NotificacionPagoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pago/pago-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPago_Activar(NotificacionPagoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pago/pago-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
