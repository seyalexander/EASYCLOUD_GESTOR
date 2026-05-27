package com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.infraestructure.persistence.model.DashboardModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaDashboard extends ResponseGeneral implements Serializable {

    private List<DashboardModel> dashboardes;
}