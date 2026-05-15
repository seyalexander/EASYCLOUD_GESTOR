package com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestEditarAllFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestEditarEstadoFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseEditarAllFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseEditarEstadoFamilia;

public interface IFamiliaEdicion {
    ResponseEditarAllFamilia EditarAllFamilia(RequestEditarAllFamilia request);
    ResponseEditarEstadoFamilia EditarEstadoFamilia(RequestEditarEstadoFamilia request, int estado);
}
