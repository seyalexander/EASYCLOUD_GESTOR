package com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestListaEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseListaEmpresa;

public interface IEmpresaListado {
    ResponseListaEmpresa listaEmpresa(RequestListaEmpresa request);
}
