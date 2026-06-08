package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSucursalDTO;

public interface INotificacionSucursal {
    void enviarNotificacionSucursal_Registro(NotificacionSucursalDTO notificacion);
    void enviarNotificacionSucursal_Edicion(NotificacionSucursalDTO notificacion);
    void enviarNotificacionSucursal_Anular(NotificacionSucursalDTO notificacion);
    void enviarNotificacionSucursal_Activar(NotificacionSucursalDTO notificacion);
}
