package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestDetalleCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseDetalleCodigoBarra;

public interface ICodigoBarraDetalle {
    ResponseDetalleCodigoBarra DetalleCodigoBarra(RequestDetalleCodigoBarra request);
}