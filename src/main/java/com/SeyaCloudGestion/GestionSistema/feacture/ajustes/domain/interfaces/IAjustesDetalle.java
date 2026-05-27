package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestDetalleAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseDetalleAjuste;

public interface IAjustesDetalle {
    ResponseDetalleAjuste DetalleAjustes(RequestDetalleAjuste request);
}