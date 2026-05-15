package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;


import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionRolDTO;

public interface INotificacionRol {
    void enviarNotificacionRol_Registro(NotificacionRolDTO notificacion);
    void enviarNotificacionRol_Edicion(NotificacionRolDTO notificacion);
    void enviarNotificacionRol_Anular(NotificacionRolDTO notificacion);
    void enviarNotificacionRol_Activar(NotificacionRolDTO notificacion);
}
