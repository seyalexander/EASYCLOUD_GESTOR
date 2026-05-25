package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class StockHistoricoModel implements Serializable {
    private long idStockHistorico;
    private long idArticulo;
    private long idAlmacen;
    private double stock;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fecha;
}
