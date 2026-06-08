package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionAlmacenDTO;

public interface INotificacionAlmacen {
    void enviarNotificacionAlmacen_Registro(NotificacionAlmacenDTO notificacion);
    void enviarNotificacionAlmacen_Edicion(NotificacionAlmacenDTO notificacion);
    void enviarNotificacionAlmacen_Anular(NotificacionAlmacenDTO notificacion);
    void enviarNotificacionAlmacen_Activar(NotificacionAlmacenDTO notificacion);
}
