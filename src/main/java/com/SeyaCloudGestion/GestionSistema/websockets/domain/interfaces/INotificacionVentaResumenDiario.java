package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionVentaResumenDiarioDTO;

public interface INotificacionVentaResumenDiario {
    void enviarNotificacionVentaResumenDiario_Registro(NotificacionVentaResumenDiarioDTO notificacion);
    void enviarNotificacionVentaResumenDiario_Edicion(NotificacionVentaResumenDiarioDTO notificacion);
    void enviarNotificacionVentaResumenDiario_Anular(NotificacionVentaResumenDiarioDTO notificacion);
    void enviarNotificacionVentaResumenDiario_Activar(NotificacionVentaResumenDiarioDTO notificacion);
}
