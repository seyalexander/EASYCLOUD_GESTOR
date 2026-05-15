package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.model.EmpleadoModel;
import lombok.Data;

@Data
public class ResponseDetalleEmpleado extends ResponseGeneral {
    EmpleadoModel empleado;
}
