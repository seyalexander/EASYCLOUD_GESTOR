package com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseDetalleSotck;

public interface ISotckDetalle {
    ResponseDetalleSotck DetalleSotck(RequestDetalleSotck request);
}