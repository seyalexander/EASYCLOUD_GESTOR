package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionRolDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionRol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionRolService implements INotificacionRol {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionRolService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public void enviarNotificacionRol_Registro(NotificacionRolDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/rol/rol-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionRol_Edicion(NotificacionRolDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/rol/rol-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionRol_Anular(NotificacionRolDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/rol/rol-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionRol_Activar(NotificacionRolDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/rol/rol-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
