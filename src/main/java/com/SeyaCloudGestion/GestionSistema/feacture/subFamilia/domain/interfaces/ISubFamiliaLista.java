package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestListaSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseListaSubFamilia;

public interface ISubFamiliaLista {
    ResponseListaSubFamilia ListaSubFamilia(RequestListaSubFamilia request);
}
