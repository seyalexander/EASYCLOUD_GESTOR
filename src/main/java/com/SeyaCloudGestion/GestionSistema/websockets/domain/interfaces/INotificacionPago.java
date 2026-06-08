package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionPagoDTO;

public interface INotificacionPago {
    void enviarNotificacionPago_Registro(NotificacionPagoDTO notificacion);
    void enviarNotificacionPago_Edicion(NotificacionPagoDTO notificacion);
    void enviarNotificacionPago_Anular(NotificacionPagoDTO notificacion);
    void enviarNotificacionPago_Activar(NotificacionPagoDTO notificacion);
}
