package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestRegistrarSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseRegistroSubFamilia;

public interface ISubFamiliaRegistro {
    ResponseRegistroSubFamilia RegistroSubFamilia(RequestRegistrarSubFamilia request);
}
