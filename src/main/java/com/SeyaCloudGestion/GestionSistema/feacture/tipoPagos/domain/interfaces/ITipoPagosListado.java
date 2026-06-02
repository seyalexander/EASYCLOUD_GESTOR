package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestListaTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseListaTipoPagos;

public interface ITipoPagosListado {
    ResponseListaTipoPagos ListaTipoPagos(RequestListaTipoPagos request);
}