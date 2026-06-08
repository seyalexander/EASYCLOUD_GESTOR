package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionAlmacenDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionAlmacen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionAlmacenService implements INotificacionAlmacen {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionAlmacenService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionAlmacen_Registro(NotificacionAlmacenDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/almacen/almacen-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionAlmacen_Edicion(NotificacionAlmacenDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/almacen/almacen-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionAlmacen_Anular(NotificacionAlmacenDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/almacen/almacen-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionAlmacen_Activar(NotificacionAlmacenDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/almacen/almacen-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
