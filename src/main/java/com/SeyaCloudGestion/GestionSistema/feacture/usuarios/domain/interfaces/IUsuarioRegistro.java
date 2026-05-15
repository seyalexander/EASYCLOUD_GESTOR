package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestRegistroUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseRegistroUsuario;

public interface IUsuarioRegistro {
    ResponseRegistroUsuario registrarUsuario(RequestRegistroUsuario request);
}
