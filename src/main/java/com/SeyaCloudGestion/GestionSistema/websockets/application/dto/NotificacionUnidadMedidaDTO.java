package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.model.UnidadMedidaModel;
import lombok.Data;

@Data
public class NotificacionUnidadMedidaDTO extends UnidadMedidaModel {
    private String tipo;
    private String mensaje;
}
