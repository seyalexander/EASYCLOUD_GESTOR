package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestRegistroSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseRegistroSucursales;

public interface ISucursalesRegistro {
    ResponseRegistroSucursales RegistroSucursales(RequestRegistroSucursales request);
}