package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionImpuestoDTO;

public interface INotificacionImpuesto {
    void enviarNotificacionImpuesto_Registro(NotificacionImpuestoDTO notificacion);
    void enviarNotificacionImpuesto_Edicion(NotificacionImpuestoDTO notificacion);
    void enviarNotificacionImpuesto_Anular(NotificacionImpuestoDTO notificacion);
    void enviarNotificacionImpuesto_Activar(NotificacionImpuestoDTO notificacion);
}
