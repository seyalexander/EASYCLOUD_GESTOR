package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionMonedaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionMoneda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionMonedaService implements INotificacionMoneda {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionMonedaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public void enviarNotificacionMoneda_Registro(NotificacionMonedaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/moneda/moneda-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMoneda_Edicion(NotificacionMonedaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/moneda/moneda-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMoneda_Anular(NotificacionMonedaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/moneda/moneda-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMoneda_Activar(NotificacionMonedaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/moneda/moneda-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionMoneda_Predeterminada(NotificacionMonedaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/moneda/moneda-predeterminada", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
