package com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestDetalleEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseDetalleEmpresa;

public interface IEmpresaDetalle {
    ResponseDetalleEmpresa DetalleEmpresa(RequestDetalleEmpresa request);
}
