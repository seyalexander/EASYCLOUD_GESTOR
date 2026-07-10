package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCajaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionCaja;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionCajaService implements INotificacionCaja {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionCajaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionCaja_Registro(NotificacionCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/aperturaCaja/aperturaCaja-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCaja_Edicion(NotificacionCajaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/aperturaCaja/aperturaCaja-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

}
