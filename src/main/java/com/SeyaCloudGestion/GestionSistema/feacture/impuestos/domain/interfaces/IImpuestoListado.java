package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestListaImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseListaImpuesto;

public interface IImpuestoListado {
    ResponseListaImpuesto listaImpuesto(RequestListaImpuesto request);
}