package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestRegistroListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseRegistroListaPrecios;

public interface IListaPreciosRegistro {
    ResponseRegistroListaPrecios RegistroListaPrecios(RequestRegistroListaPrecios request);
}