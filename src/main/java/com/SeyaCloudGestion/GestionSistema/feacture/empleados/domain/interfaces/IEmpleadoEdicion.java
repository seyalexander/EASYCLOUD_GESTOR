package com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestEditarAllEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestEditarEstadoEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseEditarAllEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseEditarEstadoEmpleado;

public interface IEmpleadoEdicion {
    ResponseEditarAllEmpleado EditarAllEmpleado(RequestEditarAllEmpleado request, long userAutenticado);
    ResponseEditarEstadoEmpleado  EditarEstadoEmpleado(RequestEditarEstadoEmpleado request, int estado, long userAutenticado);
}
