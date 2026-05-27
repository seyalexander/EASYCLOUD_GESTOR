package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionMovimientoStockDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionMovimientoStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionMovimientoStockService implements INotificacionMovimientoStock {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionMovimientoStockService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionMovimientoStock_Registro(NotificacionMovimientoStockDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/movimientoStock/movimientoStock-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMovimientoStock_Edicion(NotificacionMovimientoStockDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/movimientoStock/movimientoStock-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMovimientoStock_Anular(NotificacionMovimientoStockDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/movimientoStock/movimientoStock-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMovimientoStock_Activar(NotificacionMovimientoStockDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/movimientoStock/movimientoStock-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
