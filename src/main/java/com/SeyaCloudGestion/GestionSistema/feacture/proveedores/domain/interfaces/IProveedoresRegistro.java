package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestRegistroProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseRegistroProveedor;

public interface IProveedoresRegistro {
    ResponseRegistroProveedor RegistroProveedores(RequestRegistroProveedor request);
}