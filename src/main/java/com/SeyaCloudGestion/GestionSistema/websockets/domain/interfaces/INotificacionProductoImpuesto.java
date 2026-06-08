package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionProductoImpuestoDTO;

public interface INotificacionProductoImpuesto {
    void enviarNotificacionProductoImpuesto_Registro(NotificacionProductoImpuestoDTO notificacion);
    void enviarNotificacionProductoImpuesto_Edicion(NotificacionProductoImpuestoDTO notificacion);
    void enviarNotificacionProductoImpuesto_Anular(NotificacionProductoImpuestoDTO notificacion);
    void enviarNotificacionProductoImpuesto_Activar(NotificacionProductoImpuestoDTO notificacion);
}
