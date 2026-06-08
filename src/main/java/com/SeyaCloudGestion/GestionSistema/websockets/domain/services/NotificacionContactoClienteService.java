package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionContactoClienteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionContactoCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionContactoClienteService implements INotificacionContactoCliente {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionContactoClienteService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionContactoCliente_Registro(NotificacionContactoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/contactoCliente/contactoCliente-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionContactoCliente_Edicion(NotificacionContactoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/contactoCliente/contactoCliente-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionContactoCliente_Anular(NotificacionContactoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/contactoCliente/contactoCliente-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionContactoCliente_Activar(NotificacionContactoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/contactoCliente/contactoCliente-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
