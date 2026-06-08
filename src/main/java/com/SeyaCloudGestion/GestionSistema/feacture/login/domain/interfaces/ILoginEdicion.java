package com.SeyaCloudGestion.GestionSistema.feacture.login.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request.RequestEditarAllLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request.RequestEditarEstadoLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response.ResponseEditarAllLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response.ResponseEditarEstadoLogin;

public interface ILoginEdicion {
    ResponseEditarAllLogin EditarAllUsuario(RequestEditarAllLogin request);
    ResponseEditarEstadoLogin EditarEstadoUsuario(RequestEditarEstadoLogin request, int estado);
}