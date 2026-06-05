package com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestRegistroParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseRegistroParametros;

public interface IParametrosRegistro {
    ResponseRegistroParametros RegistroParametros(RequestRegistroParametros request);
}