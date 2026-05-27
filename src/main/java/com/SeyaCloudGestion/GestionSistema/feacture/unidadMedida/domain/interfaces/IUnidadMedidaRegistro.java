package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestRegistroUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseRegistroUnidadMedida;

public interface IUnidadMedidaRegistro {
    ResponseRegistroUnidadMedida RegistroUnidadMedida(RequestRegistroUnidadMedida request);
}