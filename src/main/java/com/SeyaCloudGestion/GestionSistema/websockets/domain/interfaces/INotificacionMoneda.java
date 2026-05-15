package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionMonedaDTO;

public interface INotificacionMoneda {
    void enviarNotificacionMoneda_Registro(NotificacionMonedaDTO notificacion);
    void enviarNotificacionMoneda_Edicion(NotificacionMonedaDTO notificacion);
    void enviarNotificacionMoneda_Anular(NotificacionMonedaDTO notificacion);
    void enviarNotificacionMoneda_Activar(NotificacionMonedaDTO notificacion);
    void enviarNotificacionMoneda_Predeterminada(NotificacionMonedaDTO notificacion);
}
