package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionDevolucionDTO;

public interface INotificacionDevolucion {
    void enviarNotificacionDevolucion_Registro(NotificacionDevolucionDTO notificacion);
    void enviarNotificacionDevolucion_Edicion(NotificacionDevolucionDTO notificacion);
    void enviarNotificacionDevolucion_Anular(NotificacionDevolucionDTO notificacion);
    void enviarNotificacionDevolucion_Activar(NotificacionDevolucionDTO notificacion);
}
