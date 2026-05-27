package com.SeyaCloudGestion.GestionSistema.feacture.promociones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request.RequestRegistroPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response.ResponseRegistroPromociones;

public interface IPromocionesRegistro {
    ResponseRegistroPromociones RegistroPromociones(RequestRegistroPromociones request);
}