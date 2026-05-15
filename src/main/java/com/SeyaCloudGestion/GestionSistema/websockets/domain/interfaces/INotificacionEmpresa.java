package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionEmpresaDTO;

public interface INotificacionEmpresa {
    void enviarNotificacionEmpresa_Registro(NotificacionEmpresaDTO notificacion);
    void enviarNotificacionEmpresa_Edicion(NotificacionEmpresaDTO notificacion);
    void enviarNotificacionEmpresa_Anular(NotificacionEmpresaDTO notificacion);
    void enviarNotificacionEmpresa_Activar(NotificacionEmpresaDTO notificacion);
}
