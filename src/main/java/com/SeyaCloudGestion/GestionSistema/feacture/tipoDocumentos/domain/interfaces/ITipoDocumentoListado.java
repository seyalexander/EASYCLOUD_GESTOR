package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestListaMonedas;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestListaTipoDocumentos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseListaTipoDocumento;

public interface ITipoDocumentoListado {
    ResponseListaTipoDocumento ListaTipoDocumento(RequestListaTipoDocumentos request);

//    long userAutenticado
}
