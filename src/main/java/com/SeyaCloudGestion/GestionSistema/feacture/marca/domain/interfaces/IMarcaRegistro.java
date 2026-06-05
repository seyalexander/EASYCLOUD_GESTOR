package com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestRegistroMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseRegistroMarca;

public interface IMarcaRegistro {
    ResponseRegistroMarca RegistroMarca(RequestRegistroMarca request);
}