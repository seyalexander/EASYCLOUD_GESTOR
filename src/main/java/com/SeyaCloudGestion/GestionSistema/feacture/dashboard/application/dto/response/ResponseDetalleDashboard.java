package com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.infraestructure.persistence.model.DashboardModel;
import lombok.Data;

@Data
public class ResponseDetalleDashboard extends ResponseGeneral {

    private DashboardModel dashboard;
}