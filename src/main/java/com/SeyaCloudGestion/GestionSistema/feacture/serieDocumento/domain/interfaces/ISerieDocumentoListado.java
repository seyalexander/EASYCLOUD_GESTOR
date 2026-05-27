package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestListaSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseListaSerieDocumento;

public interface ISerieDocumentoListado {
    ResponseListaSerieDocumento listaSerieDocumento(RequestListaSerieDocumento request);
}