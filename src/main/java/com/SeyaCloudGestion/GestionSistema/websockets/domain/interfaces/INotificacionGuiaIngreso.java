package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionGuiaIngresoDTO;

public interface INotificacionGuiaIngreso {
    void enviarNotificacionGuiaIngreso_Registro(NotificacionGuiaIngresoDTO notificacion);
    void enviarNotificacionGuiaIngreso_Edicion(NotificacionGuiaIngresoDTO notificacion);
    void enviarNotificacionGuiaIngreso_Anular(NotificacionGuiaIngresoDTO notificacion);
    void enviarNotificacionGuiaIngreso_Activar(NotificacionGuiaIngresoDTO notificacion);
}
