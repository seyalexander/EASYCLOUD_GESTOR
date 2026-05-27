package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionMarcaDTO;

public interface INotificacionMarca {
    void enviarNotificacionMarca_Registro(NotificacionMarcaDTO notificacion);
    void enviarNotificacionMarca_Edicion(NotificacionMarcaDTO notificacion);
    void enviarNotificacionMarca_Anular(NotificacionMarcaDTO notificacion);
    void enviarNotificacionMarca_Activar(NotificacionMarcaDTO notificacion);
}
