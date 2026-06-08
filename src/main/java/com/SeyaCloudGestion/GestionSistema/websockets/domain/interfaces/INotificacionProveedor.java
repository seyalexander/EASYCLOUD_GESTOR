package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionProveedorDTO;

public interface INotificacionProveedor {
    void enviarNotificacionProveedor_Registro(NotificacionProveedorDTO notificacion);
    void enviarNotificacionProveedor_Edicion(NotificacionProveedorDTO notificacion);
    void enviarNotificacionProveedor_Anular(NotificacionProveedorDTO notificacion);
    void enviarNotificacionProveedor_Activar(NotificacionProveedorDTO notificacion);
}
