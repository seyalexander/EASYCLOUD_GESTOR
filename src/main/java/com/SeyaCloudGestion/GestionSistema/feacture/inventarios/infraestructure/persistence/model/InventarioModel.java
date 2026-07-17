package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InventarioModel extends AuditableModel implements Serializable {
    private long idInventarioCabecera;
    private long idAlmacen;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaInventario;
    private String observacion;
    private EstadoInventario estado;

    private long idUsuario;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCierreInventario;
}