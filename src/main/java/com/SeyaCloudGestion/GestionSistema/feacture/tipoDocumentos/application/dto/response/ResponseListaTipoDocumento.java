package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.model.TipoDocumentoModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseListaTipoDocumento extends ResponseGeneral {
    private List<TipoDocumentoModel> tipoDocumentos;
}
