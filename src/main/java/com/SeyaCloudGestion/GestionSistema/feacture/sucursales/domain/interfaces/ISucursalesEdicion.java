package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestEditarAllSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestEditarEstadoSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseEditarAllSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseEditarEstadoSucursales;

public interface ISucursalesEdicion {
    ResponseEditarAllSucursales EditarAllSucursales(RequestEditarAllSucursales request);
    ResponseEditarEstadoSucursales EditarEstadoSucursales(RequestEditarEstadoSucursales request, int estado);
}