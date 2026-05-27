package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoClienteDTO;

public interface INotificacionTipoCliente {
    void enviarNotificacionTipoCliente_Registro(NotificacionTipoClienteDTO notificacion);
    void enviarNotificacionTipoCliente_Edicion(NotificacionTipoClienteDTO notificacion);
    void enviarNotificacionTipoCliente_Anular(NotificacionTipoClienteDTO notificacion);
    void enviarNotificacionTipoCliente_Activar(NotificacionTipoClienteDTO notificacion);
}
