package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.persistence.model.SerieCajaModel;
import lombok.Data;

@Data
public class ResponseDetalleSerieCaja extends ResponseGeneral {

    private SerieCajaModel serieCaja;
}