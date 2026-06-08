package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestRegistroProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseRegistroProveedores;

public interface IProveedoresRegistro {
    ResponseRegistroProveedores RegistroProveedores(RequestRegistroProveedores request);
}