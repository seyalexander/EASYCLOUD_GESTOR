package com.SeyaCloudGestion.GestionSistema.feacture.login.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request.RequestListaLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response.ResponseListaLogin;

public interface ILoginListado {
    ResponseListaLogin listaUsuario(RequestListaLogin request);
}