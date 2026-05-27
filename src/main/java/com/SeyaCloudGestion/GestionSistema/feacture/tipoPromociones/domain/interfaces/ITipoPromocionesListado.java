package com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.request.RequestListaTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.response.ResponseListaTipoPromociones;

public interface ITipoPromocionesListado {
    ResponseListaTipoPromociones listaTipoPromociones(RequestListaTipoPromociones request);
}