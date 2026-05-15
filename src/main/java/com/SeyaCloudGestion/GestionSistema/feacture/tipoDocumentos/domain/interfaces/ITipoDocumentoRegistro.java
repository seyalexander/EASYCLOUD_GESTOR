package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestRegistroTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseRegistroTipoDocumento;

public interface ITipoDocumentoRegistro {
    ResponseRegistroTipoDocumento RegistroTipoDocumento(RequestRegistroTipoDocumento request, long userAutenticado );
}
