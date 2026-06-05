package com.SeyaCloudGestion.GestionSistema.feacture.promociones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request.RequestListaPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response.ResponseListaPromociones;

public interface IPromocionesListado {
    ResponseListaPromociones listaPromociones(RequestListaPromociones request);
}