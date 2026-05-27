package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.model.SerieDocumentoModel;
import lombok.Data;

@Data
public class ResponseDetalleSerieDocumento extends ResponseGeneral {

    private SerieDocumentoModel serieDocumento;
}