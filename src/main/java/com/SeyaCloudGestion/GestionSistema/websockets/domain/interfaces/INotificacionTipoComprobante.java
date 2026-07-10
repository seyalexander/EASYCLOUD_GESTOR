package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoComprobanteDTO;

public interface INotificacionTipoComprobante {
    void enviarNotificacionTipoComprobante_Registro(NotificacionTipoComprobanteDTO notificacion);
    void enviarNotificacionTipoComprobante_Edicion(NotificacionTipoComprobanteDTO notificacion);
    void enviarNotificacionTipoComprobante_Anular(NotificacionTipoComprobanteDTO notificacion);
    void enviarNotificacionTipoComprobante_Activar(NotificacionTipoComprobanteDTO notificacion);
}