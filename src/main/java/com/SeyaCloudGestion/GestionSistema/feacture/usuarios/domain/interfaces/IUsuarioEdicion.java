package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestEditarAllUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestEditarEstadoUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseEditarAllUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseEditarEstadoUsuario;

public interface IUsuarioEdicion {
    ResponseEditarAllUsuario EditarUsuario(RequestEditarAllUsuario request);
    ResponseEditarEstadoUsuario  EditarEstadoUsuario(RequestEditarEstadoUsuario request, int estado);
}
