package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestListaProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseListaProveedor;

public interface IProveedoresListado {
    ResponseListaProveedor listaProveedores(RequestListaProveedor request);
}