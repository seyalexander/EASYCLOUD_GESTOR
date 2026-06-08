package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestEditarAllInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestEditarEstadoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseEditarAllInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseEditarEstadoInventario;

public interface IInventarioEdicion {
    ResponseEditarAllInventario EditarAllInventario(RequestEditarAllInventario request);
    ResponseEditarEstadoInventario EditarEstadoInventario(RequestEditarEstadoInventario request, int estado);
}