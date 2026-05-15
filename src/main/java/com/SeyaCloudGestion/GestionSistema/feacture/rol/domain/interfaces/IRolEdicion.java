package com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestEditarEstadoRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseEditarEstadoRol;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarEstadoSubFamilia;

public interface IRolEdicion {
    ResponseEditarAllRol EditarRol(RequestEditarAllRol request);
    ResponseEditarEstadoRol EditarEstadoRol(RequestEditarEstadoRol request, int estado);
}
