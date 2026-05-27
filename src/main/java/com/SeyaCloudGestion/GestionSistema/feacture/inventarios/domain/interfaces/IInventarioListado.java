package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestListaInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseListaInventario;

public interface IInventarioListado {
    ResponseListaInventario listaInventario(RequestListaInventario request);
}