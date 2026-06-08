package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionVentaResumenDiarioDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionVentaResumenDiario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionVentaResumenDiarioService implements INotificacionVentaResumenDiario {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionVentaResumenDiarioService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionVentaResumenDiario_Registro(NotificacionVentaResumenDiarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/ventaResumenDiario/ventaResumenDiario-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionVentaResumenDiario_Edicion(NotificacionVentaResumenDiarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/ventaResumenDiario/ventaResumenDiario-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionVentaResumenDiario_Anular(NotificacionVentaResumenDiarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/ventaResumenDiario/ventaResumenDiario-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionVentaResumenDiario_Activar(NotificacionVentaResumenDiarioDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/ventaResumenDiario/ventaResumenDiario-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
