package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionPagoProveedorDTO;

public interface INotificacionPagoProveedor {
    void enviarNotificacionPagoProveedor_Registro(NotificacionPagoProveedorDTO notificacion);
    void enviarNotificacionPagoProveedor_Edicion(NotificacionPagoProveedorDTO notificacion);
    void enviarNotificacionPagoProveedor_Anular(NotificacionPagoProveedorDTO notificacion);
    void enviarNotificacionPagoProveedor_Activar(NotificacionPagoProveedorDTO notificacion);
}
