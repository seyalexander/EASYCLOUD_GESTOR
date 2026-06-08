package com.SeyaCloudGestion.GestionSistema.feacture.promociones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request.RequestDetallePromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response.ResponseDetallePromociones;

public interface IPromocionesDetalle {
    ResponseDetallePromociones DetallePromociones(RequestDetallePromociones request);
}