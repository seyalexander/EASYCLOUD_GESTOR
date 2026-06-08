package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCodigoBarraDTO;

public interface INotificacionCodigoBarra {
    void enviarNotificacionCodigoBarra_Registro(NotificacionCodigoBarraDTO notificacion);
    void enviarNotificacionCodigoBarra_Edicion(NotificacionCodigoBarraDTO notificacion);
    void enviarNotificacionCodigoBarra_Anular(NotificacionCodigoBarraDTO notificacion);
    void enviarNotificacionCodigoBarra_Activar(NotificacionCodigoBarraDTO notificacion);
}
