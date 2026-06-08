package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestEditarAllSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestEditarEstadoSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseEditarAllSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseEditarEstadoSerieDocumento;

public interface ISerieDocumentoEdicion {
    ResponseEditarAllSerieDocumento EditarAllSerieDocumento(RequestEditarAllSeries request);
    ResponseEditarEstadoSerieDocumento EditarEstadoSerieDocumento(RequestEditarEstadoSeries request, int estado);
}