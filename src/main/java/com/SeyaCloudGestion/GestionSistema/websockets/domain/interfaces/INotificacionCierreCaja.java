package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSerieCajaDTO;

public interface INotificacionCierreCaja {
    void enviarNotificacionCierreCaja_Registro(NotificacionSerieCajaDTO notificacion);
    void enviarNotificacionCierreCaja_Edicion(NotificacionSerieCajaDTO notificacion);
    void enviarNotificacionCierreCaja_Anular(NotificacionSerieCajaDTO notificacion);
    void enviarNotificacionCierreCaja_Activar(NotificacionSerieCajaDTO notificacion);
}
