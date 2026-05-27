package com.SeyaCloudGestion.GestionSistema.feacture.reportes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.request.RequestListaReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response.ResponseListaReportes;

public interface IReportesListado {
    ResponseListaReportes listaReportes(RequestListaReportes request);
}