package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.persistence.model.ParametrosModel;
import lombok.Data;

@Data
public class NotificacionParametroDTO extends ParametrosModel {
    private String tipo;
    private String mensaje;
}
