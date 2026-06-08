package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.infraestructure.persistence.model.CierreCajaModel;
import lombok.Data;

@Data
public class ResponseDetalleCierreCaja extends ResponseGeneral {

    private CierreCajaModel cierreCaja;
}