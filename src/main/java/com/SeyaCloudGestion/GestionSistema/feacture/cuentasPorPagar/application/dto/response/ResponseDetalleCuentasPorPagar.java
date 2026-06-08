package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.model.CuentasPorPagarModel;
import lombok.Data;

@Data
public class ResponseDetalleCuentasPorPagar extends ResponseGeneral {

    private CuentasPorPagarModel cuentasPorPagar;
}