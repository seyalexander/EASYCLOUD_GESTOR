package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.model.CodigoBarraModel;
import lombok.Data;

@Data
public class NotificacionCodigoBarraDTO extends CodigoBarraModel {
    private String tipo;
    private String mensaje;
}
