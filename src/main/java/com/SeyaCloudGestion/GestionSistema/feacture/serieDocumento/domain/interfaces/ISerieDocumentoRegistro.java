package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestRegistroSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseRegistroSerieDocumento;

public interface ISerieDocumentoRegistro {
    ResponseRegistroSerieDocumento RegistroSerieDocumento(RequestRegistroSeries request, long correlativo);
}