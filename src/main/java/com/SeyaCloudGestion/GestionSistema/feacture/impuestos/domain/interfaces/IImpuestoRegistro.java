package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestRegistroImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseRegistroImpuesto;

public interface IImpuestoRegistro {
    ResponseRegistroImpuesto RegistroImpuesto(RequestRegistroImpuesto request);
}