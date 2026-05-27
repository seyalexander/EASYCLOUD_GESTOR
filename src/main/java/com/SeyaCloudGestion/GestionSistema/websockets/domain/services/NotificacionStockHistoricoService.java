package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionStockHistoricoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionStockHistorico;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionStockHistoricoService implements INotificacionStockHistorico {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionStockHistoricoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionStockHistorico_Registro(NotificacionStockHistoricoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/stockHistorico/stockHistorico-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionStockHistorico_Edicion(NotificacionStockHistoricoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/stockHistorico/stockHistorico-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionStockHistorico_Anular(NotificacionStockHistoricoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/stockHistorico/stockHistorico-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionStockHistorico_Activar(NotificacionStockHistoricoDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/stockHistorico/stockHistorico-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
