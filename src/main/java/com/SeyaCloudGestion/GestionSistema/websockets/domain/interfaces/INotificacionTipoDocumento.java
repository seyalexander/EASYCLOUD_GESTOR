package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoDocumentoDTO;

public interface INotificacionTipoDocumento {
    void enviarNotificacionTipoDocumento_Registro(NotificacionTipoDocumentoDTO notificacion);
    void enviarNotificacionTipoDocument_Edicion(NotificacionTipoDocumentoDTO notificacion);
    void enviarNotificacionTipoDocument_Anular(NotificacionTipoDocumentoDTO notificacion);
    void enviarNotificacionTipoDocument_Activar(NotificacionTipoDocumentoDTO notificacion);
}
