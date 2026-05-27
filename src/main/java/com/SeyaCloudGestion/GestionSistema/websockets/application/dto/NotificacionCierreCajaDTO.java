package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.infraestructure.persistence.model.CierreCajaModel;
import lombok.Data;

@Data
public class NotificacionCierreCajaDTO extends CierreCajaModel {
    private String tipo;
    private String mensaje;
}
