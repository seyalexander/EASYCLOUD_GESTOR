package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSucursalDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionSucursal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionSucursalService implements INotificacionSucursal {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionSucursalService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionSucursal_Registro(NotificacionSucursalDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/sucursal/sucursal-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSucursal_Edicion(NotificacionSucursalDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/sucursal/sucursal-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSucursal_Anular(NotificacionSucursalDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/sucursal/sucursal-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSucursal_Activar(NotificacionSucursalDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/sucursal/sucursal-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
