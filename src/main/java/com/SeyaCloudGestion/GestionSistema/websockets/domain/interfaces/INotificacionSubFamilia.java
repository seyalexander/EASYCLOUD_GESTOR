package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionFamiliaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSubFamiliaDTO;

public interface INotificacionSubFamilia {
    void enviarNotificacionSubFamilia_Registro(NotificacionSubFamiliaDTO notificacion);
    void enviarNotificacionSubFamilia_Edicion(NotificacionSubFamiliaDTO notificacion);
    void enviarNotificacionSubFamilia_Anular(NotificacionSubFamiliaDTO notificacion);
    void enviarNotificacionSubFamilia_Activar(NotificacionSubFamiliaDTO notificacion);
}
