package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionUnidadMedidaDTO;

public interface INotificacionUnidadMedida {
    void enviarNotificacionUnidadMedida_Registro(NotificacionUnidadMedidaDTO notificacion);
    void enviarNotificacionUnidadMedida_Edicion(NotificacionUnidadMedidaDTO notificacion);
    void enviarNotificacionUnidadMedida_Anular(NotificacionUnidadMedidaDTO notificacion);
    void enviarNotificacionUnidadMedida_Activar(NotificacionUnidadMedidaDTO notificacion);
}
