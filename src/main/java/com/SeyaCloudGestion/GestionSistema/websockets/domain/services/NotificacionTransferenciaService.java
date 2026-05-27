package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTransferenciaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionTransferencia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionTransferenciaService implements INotificacionTransferencia {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionTransferenciaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionTransferencia_Registro(NotificacionTransferenciaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/transferencia/transferencia-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTransferencia_Edicion(NotificacionTransferenciaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/transferencia/transferencia-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTransferencia_Anular(NotificacionTransferenciaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/transferencia/transferencia-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionTransferencia_Activar(NotificacionTransferenciaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/transferencia/transferencia-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
