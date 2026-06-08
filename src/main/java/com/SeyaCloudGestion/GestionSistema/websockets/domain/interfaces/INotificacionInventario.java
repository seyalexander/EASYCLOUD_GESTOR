package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionInventarioDTO;

public interface INotificacionInventario {
    void enviarNotificacionInventario_Registro(NotificacionInventarioDTO notificacion);
    void enviarNotificacionInventario_Edicion(NotificacionInventarioDTO notificacion);
    void enviarNotificacionInventario_Anular(NotificacionInventarioDTO notificacion);
    void enviarNotificacionInventario_Activar(NotificacionInventarioDTO notificacion);
}
