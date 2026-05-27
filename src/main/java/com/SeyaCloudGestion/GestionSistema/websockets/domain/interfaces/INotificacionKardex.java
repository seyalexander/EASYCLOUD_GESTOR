package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionKardexDTO;

public interface INotificacionKardex {
    void enviarNotificacionKardex_Registro(NotificacionKardexDTO notificacion);
    void enviarNotificacionKardex_Edicion(NotificacionKardexDTO notificacion);
    void enviarNotificacionKardex_Anular(NotificacionKardexDTO notificacion);
    void enviarNotificacionKardex_Activar(NotificacionKardexDTO notificacion);
}
