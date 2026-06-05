package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseDetalleSucursales;

public interface ISucursalesDetalle {
    ResponseDetalleSucursales DetalleSucursales(RequestDetalleSucursales request);
}