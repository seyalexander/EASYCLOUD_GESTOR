package com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EmpleadoModel implements Serializable {
    private long idEmpleado;
    private String nombre;
    private String apellido;
    private String telefono;
    private String imagenUrl;
    private long idTipoDocumento;
    private String tipOocumento;
    private String documento;
    private String fechaNacimiento;
    private String fechaIngreso;
    private int estado;

    // Auditoría
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCreacion;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaEdicion;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaAnulacion;
    private long idUsuarioCreacion;
    private long idUsuarioEdicion;
    private long idUsuarioAnulacion;
    private String usuarioCreacion;
    private String usuarioEdicion;
    private String usuarioAnulacion;
}
