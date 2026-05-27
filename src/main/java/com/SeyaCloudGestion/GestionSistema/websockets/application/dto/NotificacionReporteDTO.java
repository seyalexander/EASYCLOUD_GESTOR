package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.reportes.infraestructure.persistence.model.ReportesModel;
import lombok.Data;

@Data
public class NotificacionReporteDTO extends ReportesModel {
    private String tipo;
    private String mensaje;
}
