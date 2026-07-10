package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoComprobanteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionTipoComprobante;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacionTipoComprobanteService implements INotificacionTipoComprobante {

    private final SimpMessagingTemplate messagingTemplate;

    // Inyección limpia por constructor, sin @Autowired
    public NotificacionTipoComprobanteService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionTipoComprobante_Registro(NotificacionTipoComprobanteDTO notificacion) {
        messagingTemplate.convertAndSend("/topic/tipocomprobante", notificacion);
    }

    @Override
    public void enviarNotificacionTipoComprobante_Edicion(NotificacionTipoComprobanteDTO notificacion) {
        messagingTemplate.convertAndSend("/topic/tipocomprobante", notificacion);
    }

    @Override
    public void enviarNotificacionTipoComprobante_Anular(NotificacionTipoComprobanteDTO notificacion) {
        messagingTemplate.convertAndSend("/topic/tipocomprobante", notificacion);
    }

    @Override
    public void enviarNotificacionTipoComprobante_Activar(NotificacionTipoComprobanteDTO notificacion) {
        messagingTemplate.convertAndSend("/topic/tipocomprobante", notificacion);
    }
}