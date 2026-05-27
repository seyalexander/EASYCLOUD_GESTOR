package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.CuentasPorCobrarModel;
import lombok.Data;

@Data
public class NotificacionCuentasPorCobrarDTO extends CuentasPorCobrarModel {
    private String tipo;
    private String mensaje;
}
