package com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseDetalleMarca;

public interface IMarcaDetalle {
    ResponseDetalleMarca DetalleMarca(RequestDetalleMarca marca);
}
