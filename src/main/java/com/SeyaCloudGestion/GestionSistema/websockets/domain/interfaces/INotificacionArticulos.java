package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionArticuloDTO;

public interface INotificacionArticulos {
    void enviarNotificacionArticulo_Registro(NotificacionArticuloDTO notificacion);
    void enviarNotificacionArticulo_Edicion(NotificacionArticuloDTO notificacion);
    void enviarNotificacionArticulo_Anular(NotificacionArticuloDTO notificacion);
    void enviarNotificacionArticulo_Activar(NotificacionArticuloDTO notificacion);
}
