package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionParametroDTO;

public interface INotificacionParametro {
    void enviarNotificacionParametro_Registro(NotificacionParametroDTO notificacion);
    void enviarNotificacionParametro_Edicion(NotificacionParametroDTO notificacion);
    void enviarNotificacionParametro_Anular(NotificacionParametroDTO notificacion);
    void enviarNotificacionParametro_Activar(NotificacionParametroDTO notificacion);
}
