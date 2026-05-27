package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCodigoBarraDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionCodigoBarra;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionCodigoBarraService implements INotificacionCodigoBarra {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionCodigoBarraService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionCodigoBarra_Registro(NotificacionCodigoBarraDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/codigoBarra/codigoBarra-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCodigoBarra_Edicion(NotificacionCodigoBarraDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/codigoBarra/codigoBarra-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCodigoBarra_Anular(NotificacionCodigoBarraDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/codigoBarra/codigoBarra-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCodigoBarra_Activar(NotificacionCodigoBarraDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/codigoBarra/codigoBarra-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
