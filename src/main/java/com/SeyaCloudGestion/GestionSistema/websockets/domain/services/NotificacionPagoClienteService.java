package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionPagoClienteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionPagoCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionPagoClienteService implements INotificacionPagoCliente {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionPagoClienteService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionPagoCliente_Registro(NotificacionPagoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pagoCliente/pagoCliente-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPagoCliente_Edicion(NotificacionPagoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pagoCliente/pagoCliente-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPagoCliente_Anular(NotificacionPagoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pagoCliente/pagoCliente-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionPagoCliente_Activar(NotificacionPagoClienteDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/pagoCliente/pagoCliente-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
