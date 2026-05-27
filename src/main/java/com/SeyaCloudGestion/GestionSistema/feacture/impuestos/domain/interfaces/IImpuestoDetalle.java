package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestDetalleImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseDetalleImpuesto;

public interface IImpuestoDetalle {
    ResponseDetalleImpuesto DetalleImpuesto(RequestDetalleImpuesto request);
}