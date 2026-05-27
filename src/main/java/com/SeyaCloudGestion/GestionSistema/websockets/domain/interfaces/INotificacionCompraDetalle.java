package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCompraDetalleDTO;

public interface INotificacionCompraDetalle {
    void enviarNotificacionCompraDetalle_Registro(NotificacionCompraDetalleDTO notificacion);
    void enviarNotificacionCompraDetalle_Edicion(NotificacionCompraDetalleDTO notificacion);
    void enviarNotificacionCompraDetalle_Anular(NotificacionCompraDetalleDTO notificacion);
    void enviarNotificacionCompraDetalle_Activar(NotificacionCompraDetalleDTO notificacion);
}
