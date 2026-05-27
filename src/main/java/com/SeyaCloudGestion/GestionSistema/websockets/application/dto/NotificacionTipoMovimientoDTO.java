package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.model.TipoMovimientoModel;
import lombok.Data;

@Data
public class NotificacionTipoMovimientoDTO extends TipoMovimientoModel {
    private String tipo;
    private String mensaje;
}
