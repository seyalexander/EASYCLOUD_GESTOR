package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionUnidadMedidaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionUnidadMedida;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionUnidadMedidaService implements INotificacionUnidadMedida {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionUnidadMedidaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionUnidadMedida_Registro(NotificacionUnidadMedidaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/unidadMedida/unidadMedida-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionUnidadMedida_Edicion(NotificacionUnidadMedidaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/unidadMedida/unidadMedida-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionUnidadMedida_Anular(NotificacionUnidadMedidaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/unidadMedida/unidadMedida-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionUnidadMedida_Activar(NotificacionUnidadMedidaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/unidadMedida/unidadMedida-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
