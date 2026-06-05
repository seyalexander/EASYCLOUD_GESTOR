package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionUsuarioDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionUsuariosUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionUsuariosUsuarioService implements INotificacionUsuariosUsuario {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionUsuariosUsuarioService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionUsuariosUsuario_Registro(NotificacionUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/usuariosUsuario/usuariosUsuario-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionUsuariosUsuario_Edicion(NotificacionUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/usuariosUsuario/usuariosUsuario-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionUsuariosUsuario_Anular(NotificacionUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/usuariosUsuario/usuariosUsuario-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionUsuariosUsuario_Activar(NotificacionUsuarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/usuariosUsuario/usuariosUsuario-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
