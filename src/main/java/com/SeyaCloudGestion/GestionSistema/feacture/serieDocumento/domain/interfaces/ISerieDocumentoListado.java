package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestListaSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseListaSerieDocumento;

public interface ISerieDocumentoListado {
    ResponseListaSerieDocumento listaSerieDocumento(RequestListaSeries request);
}