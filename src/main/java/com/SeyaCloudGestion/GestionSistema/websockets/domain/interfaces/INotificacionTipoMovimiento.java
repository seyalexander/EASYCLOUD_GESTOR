package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoMovimientoDTO;

public interface INotificacionTipoMovimiento {
    void enviarNotificacionTipoMovimiento_Registro(NotificacionTipoMovimientoDTO notificacion);
    void enviarNotificacionTipoMovimiento_Edicion(NotificacionTipoMovimientoDTO notificacion);
    void enviarNotificacionTipoMovimiento_Anular(NotificacionTipoMovimientoDTO notificacion);
    void enviarNotificacionTipoMovimiento_Activar(NotificacionTipoMovimientoDTO notificacion);
}
