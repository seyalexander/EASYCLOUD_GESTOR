package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionLoginDTO;

public interface INotificacionLogin {
    void enviarNotificacionLogin_Registro(NotificacionLoginDTO notificacion);
    void enviarNotificacionLogin_Edicion(NotificacionLoginDTO notificacion);
    void enviarNotificacionLogin_Anular(NotificacionLoginDTO notificacion);
    void enviarNotificacionLogin_Activar(NotificacionLoginDTO notificacion);
}
