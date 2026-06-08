package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCompraDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionCompra;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionCompraService implements INotificacionCompra {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionCompraService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionCompra_Registro(NotificacionCompraDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/compra/compra-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCompra_Edicion(NotificacionCompraDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/compra/compra-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCompra_Anular(NotificacionCompraDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/compra/compra-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCompra_Activar(NotificacionCompraDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/compra/compra-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
