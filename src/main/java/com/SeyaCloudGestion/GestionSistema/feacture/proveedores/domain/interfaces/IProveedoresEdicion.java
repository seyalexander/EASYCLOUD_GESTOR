package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarAllProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarEstadoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseEditarAllProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseEditarEstadoProveedores;

public interface IProveedoresEdicion {
    ResponseEditarAllProveedores EditarAllProveedores(RequestEditarAllProveedores request);
    ResponseEditarEstadoProveedores EditarEstadoProveedores(RequestEditarEstadoProveedores request, int estado);
}