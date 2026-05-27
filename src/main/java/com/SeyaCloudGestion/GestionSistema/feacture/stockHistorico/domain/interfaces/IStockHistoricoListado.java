package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.request.RequestListaStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.response.ResponseListaStockHistorico;

public interface IStockHistoricoListado {
    ResponseListaStockHistorico listaStockHistorico(RequestListaStockHistorico request);
}