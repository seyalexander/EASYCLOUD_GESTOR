package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionAperturaCajaDTO;

public interface INotificacionAperturaCaja {
    void enviarNotificacionAperturaCaja_Registro(NotificacionAperturaCajaDTO notificacion);
    void enviarNotificacionAperturaCaja_Edicion(NotificacionAperturaCajaDTO notificacion);
    void enviarNotificacionAperturaCaja_Anular(NotificacionAperturaCajaDTO notificacion);
    void enviarNotificacionAperturaCaja_Activar(NotificacionAperturaCajaDTO notificacion);
}
