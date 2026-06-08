package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestDetalleProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseDetalleProveedores;

public interface IProveedoresDetalle {
    ResponseDetalleProveedores DetalleProveedores(RequestDetalleProveedores request);
}