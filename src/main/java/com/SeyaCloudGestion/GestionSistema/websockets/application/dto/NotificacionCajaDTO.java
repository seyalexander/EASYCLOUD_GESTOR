package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.model.CajaModel;
import lombok.Data;

@Data
public class NotificacionCajaDTO extends CajaModel {
    private String tipo;
    private String mensaje;
}
