package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCajaDTO;

public interface INotificacionCaja {
    void enviarNotificacionCaja_Registro(NotificacionCajaDTO notificacion);
    void enviarNotificacionCaja_Edicion(NotificacionCajaDTO notificacion);

}
