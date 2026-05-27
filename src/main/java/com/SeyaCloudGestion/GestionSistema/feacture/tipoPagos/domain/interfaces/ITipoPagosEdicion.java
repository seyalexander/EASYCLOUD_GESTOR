package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestEditarAllTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestEditarEstadoTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseEditarAllTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseEditarEstadoTipoPagos;

public interface ITipoPagosEdicion {
    ResponseEditarAllTipoPagos EditarAllTipoPagos(RequestEditarAllTipoPagos request);
    ResponseEditarEstadoTipoPagos EditarEstadoTipoPagos(RequestEditarEstadoTipoPagos request, int estado);
}