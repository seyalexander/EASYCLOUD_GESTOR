package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestListaMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseListaMovimientoStock;

public interface IMovimientoStockListado {
    ResponseListaMovimientoStock listaMovimientoStock(RequestListaMovimientoStock request);
}