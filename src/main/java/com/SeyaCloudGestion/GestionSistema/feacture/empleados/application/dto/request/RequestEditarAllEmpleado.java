package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request;

import lombok.Data;

@Data
public class RequestEditarAllEmpleado {
    private long idEmpleado;
    private String nombre;
    private String apellido;
    private String telefono;
    private String imagenUrl;
    private long idTipoDocumento;
    private String documento;
    private String fechaNacimiento;
    private String fechaIngreso;
    private int estado;
}
