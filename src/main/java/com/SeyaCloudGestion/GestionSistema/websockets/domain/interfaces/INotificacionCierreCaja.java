package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCierreCajaDTO;

public interface INotificacionCierreCaja {
    void enviarNotificacionCierreCaja_Registro(NotificacionCierreCajaDTO notificacion);
    void enviarNotificacionCierreCaja_Edicion(NotificacionCierreCajaDTO notificacion);
    void enviarNotificacionCierreCaja_Anular(NotificacionCierreCajaDTO notificacion);
    void enviarNotificacionCierreCaja_Activar(NotificacionCierreCajaDTO notificacion);
}
