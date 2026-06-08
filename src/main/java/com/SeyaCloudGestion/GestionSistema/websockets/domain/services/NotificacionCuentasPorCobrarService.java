package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCuentasPorCobrarDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionCuentasPorCobrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionCuentasPorCobrarService implements INotificacionCuentasPorCobrar {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionCuentasPorCobrarService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionCuentasPorCobrar_Registro(NotificacionCuentasPorCobrarDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cuentasPorCobrar/cuentasPorCobrar-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCuentasPorCobrar_Edicion(NotificacionCuentasPorCobrarDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cuentasPorCobrar/cuentasPorCobrar-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCuentasPorCobrar_Anular(NotificacionCuentasPorCobrarDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cuentasPorCobrar/cuentasPorCobrar-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionCuentasPorCobrar_Activar(NotificacionCuentasPorCobrarDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/cuentasPorCobrar/cuentasPorCobrar-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
