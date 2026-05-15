package com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestListaRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseListaRol;

public interface IRolListado {
    ResponseListaRol ListaRol(RequestListaRol request);
}
