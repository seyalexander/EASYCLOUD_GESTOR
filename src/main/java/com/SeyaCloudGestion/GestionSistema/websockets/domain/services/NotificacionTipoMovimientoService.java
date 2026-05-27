package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoMovimientoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionTipoMovimiento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionTipoMovimientoService implements INotificacionTipoMovimiento {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionTipoMovimientoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionTipoMovimiento_Registro(NotificacionTipoMovimientoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoMovimiento/tipoMovimiento-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoMovimiento_Edicion(NotificacionTipoMovimientoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoMovimiento/tipoMovimiento-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoMovimiento_Anular(NotificacionTipoMovimientoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoMovimiento/tipoMovimiento-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoMovimiento_Activar(NotificacionTipoMovimientoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoMovimiento/tipoMovimiento-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
