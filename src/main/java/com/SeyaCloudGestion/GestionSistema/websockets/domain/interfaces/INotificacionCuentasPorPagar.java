package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCuentasPorPagarDTO;

public interface INotificacionCuentasPorPagar {
    void enviarNotificacionCuentasPorPagar_Registro(NotificacionCuentasPorPagarDTO notificacion);
    void enviarNotificacionCuentasPorPagar_Edicion(NotificacionCuentasPorPagarDTO notificacion);
    void enviarNotificacionCuentasPorPagar_Anular(NotificacionCuentasPorPagarDTO notificacion);
    void enviarNotificacionCuentasPorPagar_Activar(NotificacionCuentasPorPagarDTO notificacion);
}
