package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSerieDocumentoDTO;

public interface INotificacionSerieDocumento {
    void enviarNotificacionSerieDocumento_Registro(NotificacionSerieDocumentoDTO notificacion);
    void enviarNotificacionSerieDocumento_Edicion(NotificacionSerieDocumentoDTO notificacion);
    void enviarNotificacionSerieDocumento_Anular(NotificacionSerieDocumentoDTO notificacion);
    void enviarNotificacionSerieDocumento_Activar(NotificacionSerieDocumentoDTO notificacion);
}
