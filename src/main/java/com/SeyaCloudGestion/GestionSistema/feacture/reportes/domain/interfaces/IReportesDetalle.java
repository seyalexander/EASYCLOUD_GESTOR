package com.SeyaCloudGestion.GestionSistema.feacture.reportes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.request.RequestDetalleReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response.ResponseDetalleReportes;

public interface IReportesDetalle {
    ResponseDetalleReportes DetalleReportes(RequestDetalleReportes request);
}