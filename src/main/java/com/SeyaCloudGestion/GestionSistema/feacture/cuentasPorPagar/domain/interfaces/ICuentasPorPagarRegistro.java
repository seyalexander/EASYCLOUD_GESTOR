package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.RequestRegistroCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseRegistroCuentasPorPagar;

public interface ICuentasPorPagarRegistro {
    ResponseRegistroCuentasPorPagar RegistroCuentasPorPagar(RequestRegistroCuentasPorPagar request);
}