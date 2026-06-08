package com.SeyaCloudGestion.GestionSistema.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonPropertyOrder({ "idUsuarioCreacion", "usuarioCreacion", "fechaCreacion",
        "idUsuarioEdicion", "usuarioEdicion", "fechaEdicion",
        "idUsuarioAnulacion", "usuarioAnulacion", "fechaAnulacion"
})
public class AuditableModel {
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
