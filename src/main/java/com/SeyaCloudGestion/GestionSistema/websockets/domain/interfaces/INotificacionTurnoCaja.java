package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTurnoCajaDTO;

public interface INotificacionTurnoCaja {
    void enviarNotificacionTurnoCaja_Registro(NotificacionTurnoCajaDTO notificacion);
    void enviarNotificacionTurnoCaja_Edicion(NotificacionTurnoCajaDTO notificacion);
    void enviarNotificacionTurnoCaja_Anular(NotificacionTurnoCajaDTO notificacion);
    void enviarNotificacionTurnoCaja_Activar(NotificacionTurnoCajaDTO notificacion);
}
