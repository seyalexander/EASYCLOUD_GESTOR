package com.SeyaCloudGestion.GestionSistema.websockets.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionStockHistoricoDTO;

public interface INotificacionStockHistorico {
    void enviarNotificacionStockHistorico_Registro(NotificacionStockHistoricoDTO notificacion);
    void enviarNotificacionStockHistorico_Edicion(NotificacionStockHistoricoDTO notificacion);
    void enviarNotificacionStockHistorico_Anular(NotificacionStockHistoricoDTO notificacion);
    void enviarNotificacionStockHistorico_Activar(NotificacionStockHistoricoDTO notificacion);
}
