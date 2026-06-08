package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionListaPrecioDTO;

public interface INotificacionListaPrecio {
    void enviarNotificacionListaPrecio_Registro(NotificacionListaPrecioDTO notificacion);
    void enviarNotificacionListaPrecio_Edicion(NotificacionListaPrecioDTO notificacion);
    void enviarNotificacionListaPrecio_Anular(NotificacionListaPrecioDTO notificacion);
    void enviarNotificacionListaPrecio_Activar(NotificacionListaPrecioDTO notificacion);
}
