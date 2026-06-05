package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.request.RequestDetalleCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response.ResponseDetalleCierreCaja;

public interface ICierreCajaDetalle {
    ResponseDetalleCierreCaja DetalleCierreCaja(RequestDetalleCierreCaja request);
}