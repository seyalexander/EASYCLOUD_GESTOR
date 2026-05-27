package com.SeyaCloudGestion.GestionSistema.feacture.dashboard.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.request.RequestEditarAllDashboard;
import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.request.RequestEditarEstadoDashboard;
import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.response.ResponseEditarAllDashboard;
import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.response.ResponseEditarEstadoDashboard;

public interface IDashboardEdicion {
    ResponseEditarAllDashboard EditarAllDashboard(RequestEditarAllDashboard request);
    ResponseEditarEstadoDashboard EditarEstadoDashboard(RequestEditarEstadoDashboard request, int estado);
}