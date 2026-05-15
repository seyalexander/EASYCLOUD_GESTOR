package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;

public interface ITipoDocumentoDetalle {
    ResponseDetalleTipoDocumento DetalleTipoDocumento(RequestDetalleTipoDocumento request);
}
