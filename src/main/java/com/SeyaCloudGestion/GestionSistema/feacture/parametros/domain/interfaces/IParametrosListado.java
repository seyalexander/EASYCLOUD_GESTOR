package com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestListaParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseListaParametros;

public interface IParametrosListado {
    ResponseListaParametros ListaParametros(RequestListaParametros request);
}