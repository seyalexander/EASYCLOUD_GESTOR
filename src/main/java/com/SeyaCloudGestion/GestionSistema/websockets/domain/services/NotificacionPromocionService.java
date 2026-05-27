package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionPromocionDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionPromocion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionPromocionService implements INotificacionPromocion {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionPromocionService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionPromocion_Registro(NotificacionPromocionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/promocion/promocion-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPromocion_Edicion(NotificacionPromocionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/promocion/promocion-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPromocion_Anular(NotificacionPromocionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/promocion/promocion-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPromocion_Activar(NotificacionPromocionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/promocion/promocion-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
