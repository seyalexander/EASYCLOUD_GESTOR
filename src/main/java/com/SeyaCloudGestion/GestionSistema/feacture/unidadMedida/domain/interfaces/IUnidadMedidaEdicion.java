package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestEditarAllUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestEditarEstadoUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseEditarAllUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseEditarEstadoUnidadMedida;

public interface IUnidadMedidaEdicion {
    ResponseEditarAllUnidadMedida EditarAllUnidadMedida(RequestEditarAllUnidadMedida request);
    ResponseEditarEstadoUnidadMedida EditarEstadoUnidadMedida(RequestEditarEstadoUnidadMedida request, int estado);
}