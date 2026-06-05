package com.SeyaCloudGestion.GestionSistema.feacture.guias.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.request.RequestListaGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response.ResponseListaGuiasIngreso;

public interface IGuiasIngresoListado {
    ResponseListaGuiasIngreso listaGuiasIngreso(RequestListaGuiasIngreso request);
}