package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionFamiliaDTO;

public interface INotificacionFamilia {
    void enviarNotificacionFamilia_Registro(NotificacionFamiliaDTO notificacion);
    void enviarNotificacionFamilia_Edicion(NotificacionFamiliaDTO notificacion);
    void enviarNotificacionFamilia_Anular(NotificacionFamiliaDTO notificacion);
    void enviarNotificacionFamilia_Activar(NotificacionFamiliaDTO notificacion);
}
