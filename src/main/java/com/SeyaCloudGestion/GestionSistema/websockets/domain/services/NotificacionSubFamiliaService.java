package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSubFamiliaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionSubFamilia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionSubFamiliaService implements INotificacionSubFamilia {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionSubFamiliaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionSubFamilia_Registro(NotificacionSubFamiliaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/subfamilia/subfamilia-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSubFamilia_Edicion(NotificacionSubFamiliaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/subfamilia/subfamilia-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSubFamilia_Anular(NotificacionSubFamiliaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/subfamilia/subfamilia-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionSubFamilia_Activar(NotificacionSubFamiliaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/subfamilia/subfamilia-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
