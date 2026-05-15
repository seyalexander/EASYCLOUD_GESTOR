package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestListaUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseListaUnidadMedida;

public interface IUnidadMedidaListado {
    ResponseListaUnidadMedida listaUnidadMedida(RequestListaUnidadMedida request);
}
