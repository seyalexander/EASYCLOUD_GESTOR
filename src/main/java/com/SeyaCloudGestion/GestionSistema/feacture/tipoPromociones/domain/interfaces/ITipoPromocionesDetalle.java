package com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.request.RequestDetalleTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.response.ResponseDetalleTipoPromociones;

public interface ITipoPromocionesDetalle {
    ResponseDetalleTipoPromociones DetalleTipoPromociones(RequestDetalleTipoPromociones request);
}