package com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestRegistroEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseRegistroEmpresa;

public interface IEmpresaRegistro {
    ResponseRegistroEmpresa registrarEmpresa(RequestRegistroEmpresa request, long id);
}