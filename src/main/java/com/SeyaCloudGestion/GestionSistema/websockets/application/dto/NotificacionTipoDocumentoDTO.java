package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.model.TipoDocumentoModel;
import lombok.Data;

@Data
public class NotificacionTipoDocumentoDTO extends TipoDocumentoModel {
    private String tipo;
    private String mensaje;
}
