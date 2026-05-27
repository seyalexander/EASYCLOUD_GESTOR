package com.SeyaCloudGestion.GestionSistema.feacture.dashboard.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.request.RequestDetalleDashboard;
import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.response.ResponseDetalleDashboard;

public interface IDashboardDetalle {
    ResponseDetalleDashboard DetalleDashboard(RequestDetalleDashboard request);
}