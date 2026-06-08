package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseDetalleSubFamilia;

public interface ISubFamiliaDetalle {
    ResponseDetalleSubFamilia DetalleSubFamilia(RequestDetalleSubFamilia request);
}
