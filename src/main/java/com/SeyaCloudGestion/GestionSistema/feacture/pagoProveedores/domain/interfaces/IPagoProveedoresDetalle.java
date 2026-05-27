package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestDetallePagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseDetallePagoProveedores;

public interface IPagoProveedoresDetalle {
    ResponseDetallePagoProveedores DetallePagoProveedores(RequestDetallePagoProveedores request);
}