package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestEditarAllImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestEditarEstadoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseEditarAllImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseEditarEstadoImpuesto;

public interface IImpuestoEdicion {
    ResponseEditarAllImpuesto EditarAllImpuesto(RequestEditarAllImpuesto request);
    ResponseEditarEstadoImpuesto EditarEstadoImpuesto(RequestEditarEstadoImpuesto request, int estado);
}