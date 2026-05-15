package com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestListaFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseListaFamilia;

public interface IFamiliaListado {
    ResponseListaFamilia listaFamilia(RequestListaFamilia request);
}
