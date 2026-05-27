package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.RequestDetalleCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseDetalleCuentasPorPagar;

public interface ICuentasPorPagarDetalle {
    ResponseDetalleCuentasPorPagar DetalleCuentasPorPagar(RequestDetalleCuentasPorPagar request);
}