package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.infraestructure.persistence.model.StockHistoricoModel;
import lombok.Data;

@Data
public class NotificacionStockHistoricoDTO extends StockHistoricoModel {
    private String tipo;
    private String mensaje;
}
