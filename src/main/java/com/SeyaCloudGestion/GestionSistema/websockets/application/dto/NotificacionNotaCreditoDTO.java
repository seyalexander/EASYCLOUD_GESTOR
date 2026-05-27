package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.model.NotaCreditoModel;
import lombok.Data;

@Data
public class NotificacionNotaCreditoDTO extends NotaCreditoModel {
    private String tipo;
    private String mensaje;
}
