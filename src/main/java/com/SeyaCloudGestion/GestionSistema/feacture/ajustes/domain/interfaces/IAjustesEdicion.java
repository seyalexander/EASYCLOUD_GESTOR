package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestEditarAllAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestEditarEstadoAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseEditarAllAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseEditarEstadoAjuste;

public interface IAjustesEdicion {
    ResponseEditarAllAjuste EditarAllAjustes(RequestEditarAllAjuste request);
    ResponseEditarEstadoAjuste EditarEstadoAjustes(RequestEditarEstadoAjuste request, int estado);
}