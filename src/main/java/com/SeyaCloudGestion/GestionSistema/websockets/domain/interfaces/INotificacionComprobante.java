package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionComprobanteDTO;

public interface INotificacionComprobante {
    void enviarNotificacionComprobante_Registro(NotificacionComprobanteDTO notificacion);
    void enviarNotificacionComprobante_Edicion(NotificacionComprobanteDTO notificacion);
    void enviarNotificacionComprobante_Anular(NotificacionComprobanteDTO notificacion);
    void enviarNotificacionComprobante_Activar(NotificacionComprobanteDTO notificacion);
}
