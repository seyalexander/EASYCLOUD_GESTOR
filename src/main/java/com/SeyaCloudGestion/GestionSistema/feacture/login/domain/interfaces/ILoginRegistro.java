package com.SeyaCloudGestion.GestionSistema.feacture.login.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request.RequestRegistroLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response.ResponseRegistroLogin;

public interface ILoginRegistro {
    ResponseRegistroLogin RegistroUsuario(RequestRegistroLogin request);
}