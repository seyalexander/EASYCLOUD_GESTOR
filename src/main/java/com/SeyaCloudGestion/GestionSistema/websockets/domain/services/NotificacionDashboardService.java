package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionDashboardDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionDashboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionDashboardService implements INotificacionDashboard {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionDashboardService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionDashboard_Registro(NotificacionDashboardDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/dashboard/dashboard-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionDashboard_Edicion(NotificacionDashboardDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/dashboard/dashboard-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionDashboard_Anular(NotificacionDashboardDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/dashboard/dashboard-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionDashboard_Activar(NotificacionDashboardDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/dashboard/dashboard-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
