package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSotckDTO;

public interface INotificacionSotck {
    void enviarNotificacionSotck_Registro(NotificacionSotckDTO notificacion);
    void enviarNotificacionSotck_Edicion(NotificacionSotckDTO notificacion);
    void enviarNotificacionSotck_Anular(NotificacionSotckDTO notificacion);
    void enviarNotificacionSotck_Activar(NotificacionSotckDTO notificacion);
}
