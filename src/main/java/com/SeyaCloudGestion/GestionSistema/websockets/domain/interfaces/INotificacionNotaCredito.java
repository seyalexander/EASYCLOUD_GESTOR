package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionNotaCreditoDTO;

public interface INotificacionNotaCredito {
    void enviarNotificacionNotaCredito_Registro(NotificacionNotaCreditoDTO notificacion);
    void enviarNotificacionNotaCredito_Edicion(NotificacionNotaCreditoDTO notificacion);
    void enviarNotificacionNotaCredito_Anular(NotificacionNotaCreditoDTO notificacion);
    void enviarNotificacionNotaCredito_Activar(NotificacionNotaCreditoDTO notificacion);
}
