package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.request.RequestDetalleStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.response.ResponseDetalleStockHistorico;

public interface IStockHistoricoDetalle {
    ResponseDetalleStockHistorico DetalleStockHistorico(RequestDetalleStockHistorico request);
}