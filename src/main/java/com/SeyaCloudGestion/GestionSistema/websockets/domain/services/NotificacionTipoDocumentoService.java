package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoDocumentoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionTipoDocumento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionTipoDocumentoService implements INotificacionTipoDocumento {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionTipoDocumentoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionTipoDocumento_Registro(NotificacionTipoDocumentoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoDocumento/tipoDocumento-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoDocument_Edicion(NotificacionTipoDocumentoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoDocumento/tipoDocumento-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoDocument_Anular(NotificacionTipoDocumentoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoDocumento/tipoDocumento-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTipoDocument_Activar(NotificacionTipoDocumentoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/tipoDocumento/tipoDocumento-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
