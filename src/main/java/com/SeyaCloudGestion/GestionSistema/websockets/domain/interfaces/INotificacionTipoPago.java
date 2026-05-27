package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoPagoDTO;

public interface INotificacionTipoPago {
    void enviarNotificacionTipoPago_Registro(NotificacionTipoPagoDTO notificacion);
    void enviarNotificacionTipoPago_Edicion(NotificacionTipoPagoDTO notificacion);
    void enviarNotificacionTipoPago_Anular(NotificacionTipoPagoDTO notificacion);
    void enviarNotificacionTipoPago_Activar(NotificacionTipoPagoDTO notificacion);
}
