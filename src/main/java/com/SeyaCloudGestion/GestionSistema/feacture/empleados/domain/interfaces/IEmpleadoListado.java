package com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestListaEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseListaEmpleado;

public interface IEmpleadoListado {
    ResponseListaEmpleado ListaEmpleado(RequestListaEmpleado request);
}
