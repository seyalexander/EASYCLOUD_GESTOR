package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCompraDTO;

public interface INotificacionCompra {
    void enviarNotificacionCompra_Registro(NotificacionCompraDTO notificacion);
    void enviarNotificacionCompra_Edicion(NotificacionCompraDTO notificacion);
    void enviarNotificacionCompra_Anular(NotificacionCompraDTO notificacion);
    void enviarNotificacionCompra_Activar(NotificacionCompraDTO notificacion);
}
