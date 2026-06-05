package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestRegistroInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseRegistroInventario;

public interface IInventarioRegistro {
    ResponseRegistroInventario RegistroInventario(RequestRegistroInventario request);
}