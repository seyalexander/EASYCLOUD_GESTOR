package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request;

import lombok.Data;

@Data
public class RequestRegistroEmpleado {
    private String nombre;
    private String apellido;
    private String telefono;
    private String imagenUrl;
    private long idTipoDocumento;
    private String tipDocumento;
    private String documento;
    private String fechaNacimiento;
    private String fechaIngreso;
}
