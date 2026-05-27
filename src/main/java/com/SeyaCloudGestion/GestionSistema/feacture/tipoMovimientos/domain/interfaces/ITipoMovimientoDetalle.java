package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;

public interface ITipoMovimientoDetalle {
    ResponseDetalleTipoMovimiento DetalleTipoMovimiento(RequestDetalleTipoMovimiento request);
}