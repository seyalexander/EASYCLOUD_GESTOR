package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionDireccionClienteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionDireccionCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionDireccionClienteService implements INotificacionDireccionCliente {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionDireccionClienteService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionDireccionCliente_Registro(NotificacionDireccionClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/direccionCliente/direccionCliente-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionDireccionCliente_Edicion(NotificacionDireccionClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/direccionCliente/direccionCliente-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionDireccionCliente_Anular(NotificacionDireccionClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/direccionCliente/direccionCliente-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionDireccionCliente_Activar(NotificacionDireccionClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/direccionCliente/direccionCliente-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
