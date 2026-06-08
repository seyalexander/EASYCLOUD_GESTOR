package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.MovimientoCajaModel;
import lombok.Data;

@Data
public class NotificacionMovimientoCajaDTO extends MovimientoCajaModel {
    private String tipo;
    private String mensaje;
}
