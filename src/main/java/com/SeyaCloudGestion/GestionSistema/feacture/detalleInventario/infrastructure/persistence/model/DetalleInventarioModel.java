package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetalleInventarioModel  {

    private long idInventarioDetalle;
    private long idInventarioCabecera;
    private long idArticulo;

    private double stockSistema;
    private double stockFisico;
    private double diferencia;

}
