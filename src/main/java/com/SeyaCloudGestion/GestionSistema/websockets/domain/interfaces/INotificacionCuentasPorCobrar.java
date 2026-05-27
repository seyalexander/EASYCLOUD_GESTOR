package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCuentasPorCobrarDTO;

public interface INotificacionCuentasPorCobrar {
    void enviarNotificacionCuentasPorCobrar_Registro(NotificacionCuentasPorCobrarDTO notificacion);
    void enviarNotificacionCuentasPorCobrar_Edicion(NotificacionCuentasPorCobrarDTO notificacion);
    void enviarNotificacionCuentasPorCobrar_Anular(NotificacionCuentasPorCobrarDTO notificacion);
    void enviarNotificacionCuentasPorCobrar_Activar(NotificacionCuentasPorCobrarDTO notificacion);
}
