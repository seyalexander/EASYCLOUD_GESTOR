package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestDetalleSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestObtenerCorrelativo;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseDetalleSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseObtenerCorrelativo;

public interface ISerieDocumentoDetalle {
    ResponseDetalleSerieDocumento DetalleSerieDocumento(RequestDetalleSeries request);
    ResponseObtenerCorrelativo ObtenerCorelativo (RequestObtenerCorrelativo request);
}