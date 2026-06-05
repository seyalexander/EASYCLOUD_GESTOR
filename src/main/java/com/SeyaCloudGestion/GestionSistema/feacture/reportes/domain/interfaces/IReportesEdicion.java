package com.SeyaCloudGestion.GestionSistema.feacture.reportes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.request.RequestEditarAllReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.request.RequestEditarEstadoReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response.ResponseEditarAllReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response.ResponseEditarEstadoReportes;

public interface IReportesEdicion {
    ResponseEditarAllReportes EditarAllReportes(RequestEditarAllReportes request);
    ResponseEditarEstadoReportes EditarEstadoReportes(RequestEditarEstadoReportes request, int estado);
}