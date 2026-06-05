package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestDetalleSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseDetalleSerieDocumento;

public interface ISerieDocumentoDetalle {
    ResponseDetalleSerieDocumento DetalleSerieDocumento(RequestDetalleSeries request);
}