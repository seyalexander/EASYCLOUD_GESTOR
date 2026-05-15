package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionFamiliaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionFamilia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionFamiliaService implements INotificacionFamilia {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionFamiliaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionFamilia_Registro(NotificacionFamiliaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/familia/familia-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionFamilia_Edicion(NotificacionFamiliaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/familia/familia-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionFamilia_Anular(NotificacionFamiliaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/familia/familia-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionFamilia_Activar(NotificacionFamiliaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/familia/familia-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
