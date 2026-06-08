package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionPagoProveedorDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionPagoProveedor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionPagoProveedorService implements INotificacionPagoProveedor {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionPagoProveedorService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionPagoProveedor_Registro(NotificacionPagoProveedorDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pagoProveedor/pagoProveedor-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPagoProveedor_Edicion(NotificacionPagoProveedorDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pagoProveedor/pagoProveedor-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPagoProveedor_Anular(NotificacionPagoProveedorDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pagoProveedor/pagoProveedor-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPagoProveedor_Activar(NotificacionPagoProveedorDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pagoProveedor/pagoProveedor-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
