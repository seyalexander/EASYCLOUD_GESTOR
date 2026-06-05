package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestDetalleMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseDetalleMovimientoStock;

public interface IMovimientoStockDetalle {
    ResponseDetalleMovimientoStock DetalleMovimientoStock(RequestDetalleMovimientoStock request);
}