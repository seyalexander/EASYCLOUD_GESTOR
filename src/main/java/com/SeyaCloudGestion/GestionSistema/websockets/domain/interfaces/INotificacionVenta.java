package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionVentaDTO;

public interface INotificacionVenta {
    void enviarNotificacionVenta_Registro(NotificacionVentaDTO notificacion);
    void enviarNotificacionVenta_Edicion(NotificacionVentaDTO notificacion);
    void enviarNotificacionVenta_Anular(NotificacionVentaDTO notificacion);
    void enviarNotificacionVenta_Activar(NotificacionVentaDTO notificacion);
}
