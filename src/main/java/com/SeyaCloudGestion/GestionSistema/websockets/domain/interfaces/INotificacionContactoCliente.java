package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionContactoClienteDTO;

public interface INotificacionContactoCliente {
    void enviarNotificacionContactoCliente_Registro(NotificacionContactoClienteDTO notificacion);
    void enviarNotificacionContactoCliente_Edicion(NotificacionContactoClienteDTO notificacion);
    void enviarNotificacionContactoCliente_Anular(NotificacionContactoClienteDTO notificacion);
    void enviarNotificacionContactoCliente_Activar(NotificacionContactoClienteDTO notificacion);
}
