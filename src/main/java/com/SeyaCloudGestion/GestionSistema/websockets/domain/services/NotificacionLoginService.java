package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionLoginDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionLoginService implements INotificacionLogin {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionLoginService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionLogin_Registro(NotificacionLoginDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/login/login-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionLogin_Edicion(NotificacionLoginDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/login/login-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionLogin_Anular(NotificacionLoginDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/login/login-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionLogin_Activar(NotificacionLoginDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/login/login-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
