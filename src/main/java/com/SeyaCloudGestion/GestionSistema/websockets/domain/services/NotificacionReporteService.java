package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionReporteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionReporte;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionReporteService implements INotificacionReporte {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionReporteService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionReporte_Registro(NotificacionReporteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/reporte/reporte-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionReporte_Edicion(NotificacionReporteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/reporte/reporte-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionReporte_Anular(NotificacionReporteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/reporte/reporte-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionReporte_Activar(NotificacionReporteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/reporte/reporte-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
