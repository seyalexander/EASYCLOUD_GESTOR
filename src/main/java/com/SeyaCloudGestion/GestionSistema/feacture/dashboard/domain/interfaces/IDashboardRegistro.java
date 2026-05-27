package com.SeyaCloudGestion.GestionSistema.feacture.dashboard.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.request.RequestRegistroDashboard;
import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.response.ResponseRegistroDashboard;

public interface IDashboardRegistro {
    ResponseRegistroDashboard RegistroDashboard(RequestRegistroDashboard request);
}