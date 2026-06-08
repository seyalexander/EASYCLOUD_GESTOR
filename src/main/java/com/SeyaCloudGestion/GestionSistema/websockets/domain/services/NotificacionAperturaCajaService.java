package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionAperturaCajaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionAperturaCaja;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionAperturaCajaService implements INotificacionAperturaCaja {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionAperturaCajaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionAperturaCaja_Registro(NotificacionAperturaCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/aperturaCaja/aperturaCaja-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionAperturaCaja_Edicion(NotificacionAperturaCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/aperturaCaja/aperturaCaja-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionAperturaCaja_Anular(NotificacionAperturaCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/aperturaCaja/aperturaCaja-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionAperturaCaja_Activar(NotificacionAperturaCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/aperturaCaja/aperturaCaja-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
