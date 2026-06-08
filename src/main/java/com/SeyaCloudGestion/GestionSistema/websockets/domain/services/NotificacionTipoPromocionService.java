package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoPromocionDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionTipoPromocion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionTipoPromocionService implements INotificacionTipoPromocion {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionTipoPromocionService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionTipoPromocion_Registro(NotificacionTipoPromocionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoPromocion/tipoPromocion-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoPromocion_Edicion(NotificacionTipoPromocionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoPromocion/tipoPromocion-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoPromocion_Anular(NotificacionTipoPromocionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoPromocion/tipoPromocion-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoPromocion_Activar(NotificacionTipoPromocionDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoPromocion/tipoPromocion-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
