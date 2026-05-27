package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionLoginUsuarioDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionLoginUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionLoginUsuarioService implements INotificacionLoginUsuario {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionLoginUsuarioService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionLoginUsuario_Registro(NotificacionLoginUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/loginUsuario/loginUsuario-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionLoginUsuario_Edicion(NotificacionLoginUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/loginUsuario/loginUsuario-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionLoginUsuario_Anular(NotificacionLoginUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/loginUsuario/loginUsuario-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionLoginUsuario_Activar(NotificacionLoginUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/loginUsuario/loginUsuario-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
