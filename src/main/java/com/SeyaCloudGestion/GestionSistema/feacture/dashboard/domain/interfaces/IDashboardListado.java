package com.SeyaCloudGestion.GestionSistema.feacture.dashboard.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.request.RequestListaDashboard;
import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.response.ResponseListaDashboard;

public interface IDashboardListado {
    ResponseListaDashboard listaDashboard(RequestListaDashboard request);
}