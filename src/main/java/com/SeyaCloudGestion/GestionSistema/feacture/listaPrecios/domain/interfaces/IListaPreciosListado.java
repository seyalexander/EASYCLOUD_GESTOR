package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestListarListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseListaListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseRegistroListaPrecios;

public interface IListaPreciosListado {
    ResponseListaListaPrecios ListarListaPrecios(RequestListarListaPrecios request);
}
