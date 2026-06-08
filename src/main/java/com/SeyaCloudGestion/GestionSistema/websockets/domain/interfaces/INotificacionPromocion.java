package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionPromocionDTO;

public interface INotificacionPromocion {
    void enviarNotificacionPromocion_Registro(NotificacionPromocionDTO notificacion);
    void enviarNotificacionPromocion_Edicion(NotificacionPromocionDTO notificacion);
    void enviarNotificacionPromocion_Anular(NotificacionPromocionDTO notificacion);
    void enviarNotificacionPromocion_Activar(NotificacionPromocionDTO notificacion);
}
