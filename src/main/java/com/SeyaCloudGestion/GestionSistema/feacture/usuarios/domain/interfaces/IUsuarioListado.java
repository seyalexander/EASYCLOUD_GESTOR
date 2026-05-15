package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestListaUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseListaUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.useCase.ListaUsuarioUseCase;

public interface IUsuarioListado {
    ResponseListaUsuario ListaUsuarios(RequestListaUsuario request);
}
