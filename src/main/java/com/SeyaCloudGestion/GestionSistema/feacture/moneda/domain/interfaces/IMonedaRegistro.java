package com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestRegistroMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseRegistroMoneda;

public interface IMonedaRegistro {
    ResponseRegistroMoneda RegistrarMoneda(RequestRegistroMoneda request, long userAutenticado);
}
