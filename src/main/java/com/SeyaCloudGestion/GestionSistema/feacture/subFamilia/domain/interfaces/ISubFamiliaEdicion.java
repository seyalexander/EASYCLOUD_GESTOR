package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarEstadoSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarEstadoSubFamilia;

public interface ISubFamiliaEdicion {
    ResponseEditarAllSubFamilia EdicionAllSubFamilia(RequestEditarAllSubFamilia request);
    ResponseEditarEstadoSubFamilia EditarEstadoSubFamilia(RequestEditarEstadoSubFamilia request, int estado);
}
