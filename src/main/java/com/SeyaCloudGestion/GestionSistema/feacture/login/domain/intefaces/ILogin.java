package com.SeyaCloudGestion.GestionSistema.feacture.login.domain.intefaces;

import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request.RequestLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response.ResponseLogin;

public interface ILogin {
    ResponseLogin Login(RequestLogin request);
}
