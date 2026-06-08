package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestRegistroPagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseRegistroPagoProveedores;

public interface IPagoProveedoresRegistro {
    ResponseRegistroPagoProveedores RegistroPagoProveedores(RequestRegistroPagoProveedores request);
}