package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionVentaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionVenta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionVentaService implements INotificacionVenta {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionVentaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionVenta_Registro(NotificacionVentaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/venta/venta-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionVenta_Edicion(NotificacionVentaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/venta/venta-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionVenta_Anular(NotificacionVentaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/venta/venta-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionVenta_Activar(NotificacionVentaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/venta/venta-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
