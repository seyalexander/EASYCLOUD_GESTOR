package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSerieDocumentoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionSerieDocumento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionSerieDocumentoService implements INotificacionSerieDocumento {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionSerieDocumentoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionSerieDocumento_Registro(NotificacionSerieDocumentoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/serieDocumento/serieDocumento-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSerieDocumento_Edicion(NotificacionSerieDocumentoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/serieDocumento/serieDocumento-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSerieDocumento_Anular(NotificacionSerieDocumentoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/serieDocumento/serieDocumento-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSerieDocumento_Activar(NotificacionSerieDocumentoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/serieDocumento/serieDocumento-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
