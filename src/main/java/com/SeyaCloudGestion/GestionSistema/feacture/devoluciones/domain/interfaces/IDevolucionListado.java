package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestListaDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseListaDevolucion;

public interface IDevolucionListado {
    ResponseListaDevolucion listaDevolucion(RequestListaDevolucion request);
}