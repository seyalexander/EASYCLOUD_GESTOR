package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestRegistrarAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseRegistroAjuste;

public interface IAjustesRegistro {
    ResponseRegistroAjuste RegistroAjustes(RequestRegistrarAjuste request);
}