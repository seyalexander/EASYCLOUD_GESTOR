package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestEditarAllListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestEditarEstadoListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseEditarAllListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseEditarEstadoListaPrecios;

public interface IListaPreciosEdicion {
    ResponseEditarAllListaPrecios EditarAllListaPrecios(RequestEditarAllListaPrecios request);
    ResponseEditarEstadoListaPrecios EditarEstadoListaPrecios(RequestEditarEstadoListaPrecios request, int estado);
}