package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionProductoImpuestoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionProductoImpuesto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionProductoImpuestoService implements INotificacionProductoImpuesto {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionProductoImpuestoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionProductoImpuesto_Registro(NotificacionProductoImpuestoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/productoImpuesto/productoImpuesto-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionProductoImpuesto_Edicion(NotificacionProductoImpuestoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/productoImpuesto/productoImpuesto-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionProductoImpuesto_Anular(NotificacionProductoImpuestoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/productoImpuesto/productoImpuesto-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionProductoImpuesto_Activar(NotificacionProductoImpuestoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/productoImpuesto/productoImpuesto-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
