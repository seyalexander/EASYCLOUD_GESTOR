package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCompraDetalleDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionCompraDetalle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionCompraDetalleService implements INotificacionCompraDetalle {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionCompraDetalleService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionCompraDetalle_Registro(NotificacionCompraDetalleDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/compraDetalle/compraDetalle-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCompraDetalle_Edicion(NotificacionCompraDetalleDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/compraDetalle/compraDetalle-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCompraDetalle_Anular(NotificacionCompraDetalleDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/compraDetalle/compraDetalle-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCompraDetalle_Activar(NotificacionCompraDetalleDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/compraDetalle/compraDetalle-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
