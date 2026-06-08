package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionClienteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionClienteService implements INotificacionCliente {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionClienteService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionCliente_Registro(NotificacionClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cliente/cliente-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCliente_Edicion(NotificacionClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cliente/cliente-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCliente_Anular(NotificacionClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cliente/cliente-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCliente_Activar(NotificacionClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cliente/cliente-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
