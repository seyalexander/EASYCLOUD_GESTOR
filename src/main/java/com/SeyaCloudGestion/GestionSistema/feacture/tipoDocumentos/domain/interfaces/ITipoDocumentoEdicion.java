package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestEditarAllTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestEditarEstadoTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseEditarAllTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseEditarEstadoTipoDocumento;

public interface ITipoDocumentoEdicion {
    ResponseEditarAllTipoDocumento EditarTipoDocumento(RequestEditarAllTipoDocumento request, long userAutenticado);
    ResponseEditarEstadoTipoDocumento EditarEstadoTipoDocumento(RequestEditarEstadoTipoDocumento request, int estado, long userAutenticado);
}
