package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionReporteDTO;

public interface INotificacionReporte {
    void enviarNotificacionReporte_Registro(NotificacionReporteDTO notificacion);
    void enviarNotificacionReporte_Edicion(NotificacionReporteDTO notificacion);
    void enviarNotificacionReporte_Anular(NotificacionReporteDTO notificacion);
    void enviarNotificacionReporte_Activar(NotificacionReporteDTO notificacion);
}
