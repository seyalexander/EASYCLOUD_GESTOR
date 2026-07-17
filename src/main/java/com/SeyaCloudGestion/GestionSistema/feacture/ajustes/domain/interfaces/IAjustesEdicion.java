package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestRegistrarAjusteInventarioInicial;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestRegistroDetalleInventarioIncial;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseRegistrarInventarioInicalAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseEditarEstadoAjuste;

public interface IAjustesEdicion {
    ResponseRegistrarInventarioInicalAjuste EditarAllAjustes(RequestRegistrarAjusteInventarioInicial request);
    ResponseEditarEstadoAjuste EditarEstadoAjustes(RequestRegistroDetalleInventarioIncial request, int estado);
}