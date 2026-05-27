package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTransferenciaDTO;

public interface INotificacionTransferencia {
    void enviarNotificacionTransferencia_Registro(NotificacionTransferenciaDTO notificacion);
    void enviarNotificacionTransferencia_Edicion(NotificacionTransferenciaDTO notificacion);
    void enviarNotificacionTransferencia_Anular(NotificacionTransferenciaDTO notificacion);
    void enviarNotificacionTransferencia_Activar(NotificacionTransferenciaDTO notificacion);
}
