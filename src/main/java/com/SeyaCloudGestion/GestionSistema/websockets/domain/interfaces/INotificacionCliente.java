package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionClienteDTO;

public interface INotificacionCliente {
    void enviarNotificacionCliente_Registro(NotificacionClienteDTO notificacion);
    void enviarNotificacionCliente_Edicion(NotificacionClienteDTO notificacion);
    void enviarNotificacionCliente_Anular(NotificacionClienteDTO notificacion);
    void enviarNotificacionCliente_Activar(NotificacionClienteDTO notificacion);
}
