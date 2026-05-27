package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.persistence.model.ImpuestoModel;
import lombok.Data;

@Data
public class NotificacionImpuestoDTO extends ImpuestoModel {
    private String tipo;
    private String mensaje;
}
