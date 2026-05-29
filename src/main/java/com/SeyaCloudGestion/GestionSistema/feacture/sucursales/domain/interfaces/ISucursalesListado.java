package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestListaSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseListaSucursales;

public interface ISucursalesListado {
    ResponseListaSucursales ListaSucursales(RequestListaSucursales request);
}