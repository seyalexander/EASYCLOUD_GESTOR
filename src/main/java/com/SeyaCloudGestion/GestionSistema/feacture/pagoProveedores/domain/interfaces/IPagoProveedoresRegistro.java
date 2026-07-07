package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestRegistroPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseRegistroPagoProveedor;

public interface IPagoProveedoresRegistro {
    ResponseRegistroPagoProveedor RegistroPagoProveedor(RequestRegistroPagoProveedor request);
}