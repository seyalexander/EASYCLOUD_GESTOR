package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoPromocionDTO;

public interface INotificacionTipoPromocion {
    void enviarNotificacionTipoPromocion_Registro(NotificacionTipoPromocionDTO notificacion);
    void enviarNotificacionTipoPromocion_Edicion(NotificacionTipoPromocionDTO notificacion);
    void enviarNotificacionTipoPromocion_Anular(NotificacionTipoPromocionDTO notificacion);
    void enviarNotificacionTipoPromocion_Activar(NotificacionTipoPromocionDTO notificacion);
}
