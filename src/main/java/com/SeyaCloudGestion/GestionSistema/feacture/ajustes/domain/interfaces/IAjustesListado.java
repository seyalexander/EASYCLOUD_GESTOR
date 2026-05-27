package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestListaAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseListaAjuste;

public interface IAjustesListado {
    ResponseListaAjuste listaAjustes(RequestListaAjuste request);
}