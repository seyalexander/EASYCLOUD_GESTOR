package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionEmpleadoDTO;

public interface INotificacionEmpleado {
    void enviarNotificacionEmpleado_Registro(NotificacionEmpleadoDTO notificacion);
    void enviarNotificacionEmpleado_Edicion(NotificacionEmpleadoDTO notificacion);
    void enviarNotificacionEmpleado_Anular(NotificacionEmpleadoDTO notificacion);
    void enviarNotificacionEmpleado_Activar(NotificacionEmpleadoDTO notificacion);
}
