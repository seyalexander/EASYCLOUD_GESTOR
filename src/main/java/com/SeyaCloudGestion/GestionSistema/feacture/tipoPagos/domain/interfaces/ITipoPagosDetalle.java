package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;

public interface ITipoPagosDetalle {
    ResponseDetalleTipoPagos DetalleTipoPagos(RequestDetalleTipoPagos request);
}