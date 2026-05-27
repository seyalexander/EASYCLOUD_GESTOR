package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.model.SerieDocumentoModel;
import lombok.Data;

@Data
public class NotificacionSerieDocumentoDTO extends SerieDocumentoModel {
    private String tipo;
    private String mensaje;
}
