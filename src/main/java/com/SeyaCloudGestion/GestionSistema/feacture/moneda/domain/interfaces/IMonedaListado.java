package com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestListaMonedas;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseListaMoneda;

public interface IMonedaListado {
    ResponseListaMoneda ListaMoneda(RequestListaMonedas request);
}
