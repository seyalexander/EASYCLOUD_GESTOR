package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.persistence.model.TipoPagosModel;
import lombok.Data;

@Data
public class NotificacionTipoPagoDTO extends TipoPagosModel {
    private String tipo;
    private String mensaje;
}
