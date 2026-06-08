package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionLoginUsuarioDTO;

public interface INotificacionLoginUsuario {
    void enviarNotificacionLoginUsuario_Registro(NotificacionLoginUsuarioDTO notificacion);
    void enviarNotificacionLoginUsuario_Edicion(NotificacionLoginUsuarioDTO notificacion);
    void enviarNotificacionLoginUsuario_Anular(NotificacionLoginUsuarioDTO notificacion);
    void enviarNotificacionLoginUsuario_Activar(NotificacionLoginUsuarioDTO notificacion);
}
