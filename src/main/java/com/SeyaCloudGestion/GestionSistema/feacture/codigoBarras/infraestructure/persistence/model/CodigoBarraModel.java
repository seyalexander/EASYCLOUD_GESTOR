package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class CodigoBarraModel extends AuditableModel  implements Serializable {
    private long idCodigoBarra;
    private long idArticulo;
    private String codigo;
    private int principal;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
