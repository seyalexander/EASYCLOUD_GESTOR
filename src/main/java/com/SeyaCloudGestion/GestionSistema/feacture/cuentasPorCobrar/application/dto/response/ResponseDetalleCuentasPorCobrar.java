package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.CuentasPorCobrarModel;
import lombok.Data;

@Data
public class ResponseDetalleCuentasPorCobrar extends ResponseGeneral {

    private CuentasPorCobrarModel cuentasPorCobrar;
}