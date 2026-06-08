package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.model.MovimientoStockModel;
import lombok.Data;

@Data
public class NotificacionMovimientoStockDTO extends MovimientoStockModel {
    private String tipo;
    private String mensaje;
}
