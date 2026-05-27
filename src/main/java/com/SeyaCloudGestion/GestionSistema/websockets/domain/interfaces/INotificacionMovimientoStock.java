package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionMovimientoStockDTO;

public interface INotificacionMovimientoStock {
    void enviarNotificacionMovimientoStock_Registro(NotificacionMovimientoStockDTO notificacion);
    void enviarNotificacionMovimientoStock_Edicion(NotificacionMovimientoStockDTO notificacion);
    void enviarNotificacionMovimientoStock_Anular(NotificacionMovimientoStockDTO notificacion);
    void enviarNotificacionMovimientoStock_Activar(NotificacionMovimientoStockDTO notificacion);
}
