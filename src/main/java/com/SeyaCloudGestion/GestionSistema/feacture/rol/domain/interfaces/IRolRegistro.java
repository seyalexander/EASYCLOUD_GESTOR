package com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestRegistroRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseRegistroRol;

public interface IRolRegistro {
    ResponseRegistroRol registrarRol(RequestRegistroRol request);
}
