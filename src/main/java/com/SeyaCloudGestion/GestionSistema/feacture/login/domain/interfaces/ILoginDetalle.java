package com.SeyaCloudGestion.GestionSistema.feacture.login.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request.RequestDetalleLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response.ResponseDetalleLogin;

public interface ILoginDetalle {
    ResponseDetalleLogin DetalleUsuario(RequestDetalleLogin request);
}