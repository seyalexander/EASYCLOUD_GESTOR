package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionUsuarioDTO;

public interface INotificacionUsuariosUsuario {
    void enviarNotificacionUsuariosUsuario_Registro(NotificacionUsuarioDTO notificacion);
    void enviarNotificacionUsuariosUsuario_Edicion(NotificacionUsuarioDTO notificacion);
    void enviarNotificacionUsuariosUsuario_Anular(NotificacionUsuarioDTO notificacion);
    void enviarNotificacionUsuariosUsuario_Activar(NotificacionUsuarioDTO notificacion);
}
