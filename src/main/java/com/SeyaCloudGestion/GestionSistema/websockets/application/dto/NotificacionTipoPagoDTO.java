package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.persistence.model.TipoPagoModel;
import lombok.Data;

@Data
public class NotificacionTipoPagoDTO extends TipoPagoModel {
    private String tipo;
    private String mensaje;
}
