package com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class KardexModel implements Serializable {

    private long idKardex;
    private long idArticulo;
    private long idAlmacen;

    private LocalDateTime fecha;
    private TipoMovimientoKardex tipoMovimiento;
    private double cantidadEntrada;
    private double costoEntrada;

    private double cantidadSalida;
    private double costoSalida;

    private double saldoCantidad;
    private double saldoCosto;
    private LocalDateTime fechaCreacion;
    private long idUsuarioCreacion;
}