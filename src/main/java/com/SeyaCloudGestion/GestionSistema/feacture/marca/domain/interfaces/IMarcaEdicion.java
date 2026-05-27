package com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestEditarAllMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestEditarEstadoMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseEditarAllMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseEditarEstadoMarca;

public interface IMarcaEdicion {
    ResponseEditarAllMarca EditarAllMarca(RequestEditarAllMarca request);
    ResponseEditarEstadoMarca EditarEstadoMarca(RequestEditarEstadoMarca request, int estado);
}