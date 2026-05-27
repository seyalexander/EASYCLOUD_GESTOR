package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.model.ComprobanteModel;
import lombok.Data;

@Data
public class NotificacionComprobanteDTO extends ComprobanteModel {
    private String tipo;
    private String mensaje;
}
