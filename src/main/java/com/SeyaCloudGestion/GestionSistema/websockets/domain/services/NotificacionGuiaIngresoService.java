package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionGuiaIngresoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionGuiaIngreso;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionGuiaIngresoService implements INotificacionGuiaIngreso {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionGuiaIngresoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionGuiaIngreso_Registro(NotificacionGuiaIngresoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/guiaIngreso/guiaIngreso-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionGuiaIngreso_Edicion(NotificacionGuiaIngresoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/guiaIngreso/guiaIngreso-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionGuiaIngreso_Anular(NotificacionGuiaIngresoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/guiaIngreso/guiaIngreso-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionGuiaIngreso_Activar(NotificacionGuiaIngresoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/guiaIngreso/guiaIngreso-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
