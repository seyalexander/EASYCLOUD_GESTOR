package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionInventarioDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionInventario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionInventarioService implements INotificacionInventario {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionInventarioService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionInventario_Registro(NotificacionInventarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/inventario/inventario-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionInventario_Edicion(NotificacionInventarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/inventario/inventario-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionInventario_Anular(NotificacionInventarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/inventario/inventario-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionInventario_Activar(NotificacionInventarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/inventario/inventario-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
