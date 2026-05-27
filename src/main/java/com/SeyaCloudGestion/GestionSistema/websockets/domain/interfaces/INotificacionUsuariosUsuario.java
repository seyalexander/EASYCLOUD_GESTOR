package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionUsuariosUsuarioDTO;

public interface INotificacionUsuariosUsuario {
    void enviarNotificacionUsuariosUsuario_Registro(NotificacionUsuariosUsuarioDTO notificacion);
    void enviarNotificacionUsuariosUsuario_Edicion(NotificacionUsuariosUsuarioDTO notificacion);
    void enviarNotificacionUsuariosUsuario_Anular(NotificacionUsuariosUsuarioDTO notificacion);
    void enviarNotificacionUsuariosUsuario_Activar(NotificacionUsuariosUsuarioDTO notificacion);
}
