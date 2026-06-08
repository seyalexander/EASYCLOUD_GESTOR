package com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestDetallePago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseDetallePago;

public interface IPagoDetalle {
    ResponseDetallePago DetallePago(RequestDetallePago request);
}