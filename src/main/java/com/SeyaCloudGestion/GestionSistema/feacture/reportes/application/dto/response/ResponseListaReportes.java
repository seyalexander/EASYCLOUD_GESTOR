package com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.infraestructure.persistence.model.ReportesModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaReportes extends ResponseGeneral implements Serializable {

    private List<ReportesModel> reportes;
}