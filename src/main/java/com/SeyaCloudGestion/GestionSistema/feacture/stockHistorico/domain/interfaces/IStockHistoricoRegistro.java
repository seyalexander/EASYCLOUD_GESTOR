package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.request.RequestRegistroStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.response.ResponseRegistroStockHistorico;

public interface IStockHistoricoRegistro {
    ResponseRegistroStockHistorico RegistroStockHistorico(RequestRegistroStockHistorico request);
}