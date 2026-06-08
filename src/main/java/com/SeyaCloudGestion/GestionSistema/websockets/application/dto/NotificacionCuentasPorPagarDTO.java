package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.model.CuentasPorPagarModel;
import lombok.Data;

@Data
public class NotificacionCuentasPorPagarDTO extends CuentasPorPagarModel {
    private String tipo;
    private String mensaje;
}
