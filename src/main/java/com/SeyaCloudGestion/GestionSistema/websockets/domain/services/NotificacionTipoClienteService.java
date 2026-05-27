package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoClienteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionTipoCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionTipoClienteService implements INotificacionTipoCliente {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionTipoClienteService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionTipoCliente_Registro(NotificacionTipoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoCliente/tipoCliente-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoCliente_Edicion(NotificacionTipoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoCliente/tipoCliente-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoCliente_Anular(NotificacionTipoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoCliente/tipoCliente-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoCliente_Activar(NotificacionTipoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoCliente/tipoCliente-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
