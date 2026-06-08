package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionProveedorDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionProveedor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionProveedorService implements INotificacionProveedor {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionProveedorService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionProveedor_Registro(NotificacionProveedorDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/proveedor/proveedor-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionProveedor_Edicion(NotificacionProveedorDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/proveedor/proveedor-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionProveedor_Anular(NotificacionProveedorDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/proveedor/proveedor-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionProveedor_Activar(NotificacionProveedorDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/proveedor/proveedor-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
