package com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestEditarAllParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestEditarEstadoParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseEditarAllParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseEditarEstadoParametros;

public interface IParametrosEdicion {
    ResponseEditarAllParametros EditarAllParametros(RequestEditarAllParametros request);
    ResponseEditarEstadoParametros EditarEstadoParametros(RequestEditarEstadoParametros request, int estado);
}