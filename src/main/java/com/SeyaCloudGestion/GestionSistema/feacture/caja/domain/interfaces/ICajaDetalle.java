package com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestDetalleCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseDetalleCaja;

public interface ICajaDetalle {
    ResponseDetalleCaja DetalleCaja(RequestDetalleCaja request);
}