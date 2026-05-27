package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseDetalleUnidadMedida;

public interface IUnidadMedidaDetalle {
    ResponseDetalleUnidadMedida DetalleUnidadMedida(RequestDetalleUnidadMedida request);
}