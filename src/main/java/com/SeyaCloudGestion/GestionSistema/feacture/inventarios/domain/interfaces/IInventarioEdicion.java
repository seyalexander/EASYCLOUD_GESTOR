package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestAjustarInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestConteoFisicoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseAjustarInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseConteoFisicoInventario;

public interface IInventarioEdicion {
    ResponseConteoFisicoInventario ConteoFisicoInventario(RequestConteoFisicoInventario request);
    ResponseAjustarInventario AjusteInventario(RequestAjustarInventario request);
}