package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.dashboard.infraestructure.persistence.model.DashboardModel;
import lombok.Data;

@Data
public class NotificacionDashboardDTO extends DashboardModel {
    private String tipo;
    private String mensaje;
}
