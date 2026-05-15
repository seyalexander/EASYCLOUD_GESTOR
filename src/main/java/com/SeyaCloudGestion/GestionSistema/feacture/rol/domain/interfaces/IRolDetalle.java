package com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestDetalleRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseDetalleRol;

public interface IRolDetalle {
    ResponseDetalleRol DetalleRol (RequestDetalleRol request);
}
