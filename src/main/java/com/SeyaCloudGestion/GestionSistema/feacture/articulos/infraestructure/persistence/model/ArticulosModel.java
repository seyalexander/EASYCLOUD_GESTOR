package com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ArticulosModel implements Serializable {

    private long idArticulos;
    private String imagenUrl;
    private String descripcion;
    private String codigoArticulo;
    private String codigoBarras;
    private float precioVenta;
    private int estado;
    private long isFamilia;
    private String descripcionFamilia;
    private long idSubFamilia;
    private String descripcionSubFamilia;
    private long  idUnidadMedida;
    private String descripcionUnidadMedida;
    private float costoCompra;
    private float stockMinimo;
    private long idMarca;
    private LocalDateTime fechaIngreso;

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
