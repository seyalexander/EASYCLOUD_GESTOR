package com.SeyaCloudGestion.GestionSistema.feacture.reportes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.request.RequestRegistroReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response.ResponseRegistroReportes;

public interface IReportesRegistro {
    ResponseRegistroReportes RegistroReportes(RequestRegistroReportes request);
}