package com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseDetalleFamilia;

public interface IFamiliaDetalle {
    ResponseDetalleFamilia DetalleFamilia(RequestDetalleFamilia request);
}
