package com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestRegistroFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseRegistroFamilia;

public interface IFamiliaRegistro {
    ResponseRegistroFamilia RegistroFamilia(RequestRegistroFamilia request);
}
