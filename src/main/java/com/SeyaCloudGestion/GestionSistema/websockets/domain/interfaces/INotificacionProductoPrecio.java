package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionProductoPrecioDTO;

public interface INotificacionProductoPrecio {
    void enviarNotificacionProductoPrecio_Registro(NotificacionProductoPrecioDTO notificacion);
    void enviarNotificacionProductoPrecio_Edicion(NotificacionProductoPrecioDTO notificacion);
    void enviarNotificacionProductoPrecio_Anular(NotificacionProductoPrecioDTO notificacion);
    void enviarNotificacionProductoPrecio_Activar(NotificacionProductoPrecioDTO notificacion);
}
