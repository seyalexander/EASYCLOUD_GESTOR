package com.SeyaCloudGestion.GestionSistema.websockets.domain.services;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionEmpresaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces.INotificacionEmpresa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionEmpresaService implements INotificacionEmpresa {
    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionEmpresaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void enviarNotificacionEmpresa_Registro(NotificacionEmpresaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/empresa/empresa-registro", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionEmpresa_Edicion(NotificacionEmpresaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/empresa/empresa-edicion", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionEmpresa_Anular(NotificacionEmpresaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/empresa/empresa-anular", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }

    @Override
    public void enviarNotificacionEmpresa_Activar(NotificacionEmpresaDTO notificacion) {
        try {
            messagingTemplate.convertAndSend("/topic/empresa/empresa-activar", notificacion);
        } catch (Exception e) {
            log.error("Error al enviar notificación WebSocket", e);
        }
    }
}
