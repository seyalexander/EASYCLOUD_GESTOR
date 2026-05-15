package com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestRegistroEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseRegistroEmpleado;

public interface IEmpleadoRegistro {
    ResponseRegistroEmpleado RegistroEmpleado(RequestRegistroEmpleado request, long userAutenticado, long idEmpresa);
}
