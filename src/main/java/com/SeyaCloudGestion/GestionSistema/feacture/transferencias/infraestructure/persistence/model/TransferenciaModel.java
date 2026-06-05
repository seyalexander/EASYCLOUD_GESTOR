package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.model;

import lombok.Data;

@Data
public class TransferenciaModel {
    private long idTransferencia;
    private long idAlmacenOrigen;
    private long idAlmacenDestino;
    private String fecha;
    private String estado;
}
