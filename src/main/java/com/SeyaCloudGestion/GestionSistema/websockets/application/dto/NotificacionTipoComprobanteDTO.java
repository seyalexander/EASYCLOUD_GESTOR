package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.infraestructure.persistence.model.AjustesModel;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.model.TipoComprobanteModel;
import lombok.Data;

@Data
public class NotificacionTipoComprobanteDTO extends TipoComprobanteModel {
    private String tipo;
    private String mensaje;
}
