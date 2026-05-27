package com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseDetalleKardex;

public interface IKardexDetalle {
    ResponseDetalleKardex DetalleKardex(RequestDetalleKardex request);
}