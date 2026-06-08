package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestRegistroTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseRegistroTipoPagos;

public interface ITipoPagosRegistro {
    ResponseRegistroTipoPagos RegistroTipoPagos(RequestRegistroTipoPagos request);
}