package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestDetalleUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseDetalleUsuario;

public interface IUsuarioDetalle {
    ResponseDetalleUsuario DetalleUsuario(RequestDetalleUsuario request);
}
