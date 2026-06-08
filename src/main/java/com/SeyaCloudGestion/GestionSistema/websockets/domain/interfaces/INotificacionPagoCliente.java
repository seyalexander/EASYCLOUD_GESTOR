package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionPagoClienteDTO;

public interface INotificacionPagoCliente {
    void enviarNotificacionPagoCliente_Registro(NotificacionPagoClienteDTO notificacion);
    void enviarNotificacionPagoCliente_Edicion(NotificacionPagoClienteDTO notificacion);
    void enviarNotificacionPagoCliente_Anular(NotificacionPagoClienteDTO notificacion);
    void enviarNotificacionPagoCliente_Activar(NotificacionPagoClienteDTO notificacion);
}
