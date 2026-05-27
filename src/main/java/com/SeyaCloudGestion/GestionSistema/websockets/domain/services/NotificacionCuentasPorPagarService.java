package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCuentasPorPagarDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionCuentasPorPagar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionCuentasPorPagarService implements INotificacionCuentasPorPagar {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionCuentasPorPagarService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionCuentasPorPagar_Registro(NotificacionCuentasPorPagarDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cuentasPorPagar/cuentasPorPagar-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCuentasPorPagar_Edicion(NotificacionCuentasPorPagarDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cuentasPorPagar/cuentasPorPagar-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCuentasPorPagar_Anular(NotificacionCuentasPorPagarDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cuentasPorPagar/cuentasPorPagar-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCuentasPorPagar_Activar(NotificacionCuentasPorPagarDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cuentasPorPagar/cuentasPorPagar-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
