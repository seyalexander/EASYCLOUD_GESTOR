package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionDashboardDTO;

public interface INotificacionDashboard {
    void enviarNotificacionDashboard_Registro(NotificacionDashboardDTO notificacion);
    void enviarNotificacionDashboard_Edicion(NotificacionDashboardDTO notificacion);
    void enviarNotificacionDashboard_Anular(NotificacionDashboardDTO notificacion);
    void enviarNotificacionDashboard_Activar(NotificacionDashboardDTO notificacion);
}
