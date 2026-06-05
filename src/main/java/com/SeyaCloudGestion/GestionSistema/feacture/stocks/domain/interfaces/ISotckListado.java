package com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestListaSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseListaSotck;

public interface ISotckListado {
    ResponseListaSotck listaSotck(RequestListaSotck request);
}