package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionMovimientoCajaDTO;

public interface INotificacionMovimientoCaja {
    void enviarNotificacionMovimientoCaja_Registro(NotificacionMovimientoCajaDTO notificacion);
    void enviarNotificacionMovimientoCaja_Edicion(NotificacionMovimientoCajaDTO notificacion);
    void enviarNotificacionMovimientoCaja_Anular(NotificacionMovimientoCajaDTO notificacion);
    void enviarNotificacionMovimientoCaja_Activar(NotificacionMovimientoCajaDTO notificacion);
}
