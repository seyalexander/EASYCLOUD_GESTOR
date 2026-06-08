package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionDireccionClienteDTO;

public interface INotificacionDireccionCliente {
    void enviarNotificacionDireccionCliente_Registro(NotificacionDireccionClienteDTO notificacion);
    void enviarNotificacionDireccionCliente_Edicion(NotificacionDireccionClienteDTO notificacion);
    void enviarNotificacionDireccionCliente_Anular(NotificacionDireccionClienteDTO notificacion);
    void enviarNotificacionDireccionCliente_Activar(NotificacionDireccionClienteDTO notificacion);
}
