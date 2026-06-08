package com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.request.RequestRegistroTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.response.ResponseRegistroTipoPromociones;

public interface ITipoPromocionesRegistro {
    ResponseRegistroTipoPromociones RegistroTipoPromociones(RequestRegistroTipoPromociones request);
}