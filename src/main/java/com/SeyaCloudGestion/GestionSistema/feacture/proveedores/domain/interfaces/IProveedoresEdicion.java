package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarAllProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarEstadoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseEditarAllProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseEditarEstadoProveedor;

public interface IProveedoresEdicion {
    ResponseEditarAllProveedor EditarAllProveedores(RequestEditarAllProveedor request);
    ResponseEditarEstadoProveedor EditarEstadoProveedores(RequestEditarEstadoProveedor request, int estado);
}