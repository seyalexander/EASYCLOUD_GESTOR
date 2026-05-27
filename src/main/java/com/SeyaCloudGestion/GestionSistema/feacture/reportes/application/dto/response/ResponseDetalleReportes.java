package com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.infraestructure.persistence.model.ReportesModel;
import lombok.Data;

@Data
public class ResponseDetalleReportes extends ResponseGeneral {

    private ReportesModel reportes;
}