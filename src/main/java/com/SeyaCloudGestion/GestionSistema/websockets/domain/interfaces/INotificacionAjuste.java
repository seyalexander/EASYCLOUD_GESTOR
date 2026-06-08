package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionAjusteDTO;

public interface INotificacionAjuste {
    void enviarNotificacionAjuste_Registro(NotificacionAjusteDTO notificacion);
    void enviarNotificacionAjuste_Edicion(NotificacionAjusteDTO notificacion);
    void enviarNotificacionAjuste_Anular(NotificacionAjusteDTO notificacion);
    void enviarNotificacionAjuste_Activar(NotificacionAjusteDTO notificacion);
}
