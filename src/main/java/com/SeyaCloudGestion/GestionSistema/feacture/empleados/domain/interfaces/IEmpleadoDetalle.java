package com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestDetalleEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseDetalleEmpleado;

public interface IEmpleadoDetalle {
    ResponseDetalleEmpleado DetalleEmpleado(RequestDetalleEmpleado request);
}
