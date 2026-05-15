package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionUsuarioDTO;

public interface INotificacionUsuario {
    void enviarNotificacionUsuario_Registro(NotificacionUsuarioDTO notificacion);
    void enviarNotificacionUsuario_Edicion(NotificacionUsuarioDTO notificacion);
    void enviarNotificacionUsuario_Anular(NotificacionUsuarioDTO notificacion);
    void enviarNotificacionUsuario_Activar(NotificacionUsuarioDTO notificacion);
}
