package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionUsuarioDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionUsuarioService implements INotificacionUsuario {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionUsuarioService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public void enviarNotificacionUsuario_Registro(NotificacionUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/usuario/usuario-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionUsuario_Edicion(NotificacionUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/usuario/usuario-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionUsuario_Anular(NotificacionUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/usuario/usuario-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionUsuario_Activar(NotificacionUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/usuario/usuario-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
