package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroComprobante {
    @Min(value = 1, message = "El id de venta debe ser mayor a 0")
    private long idVenta;

    @Min(value = 1, message = "El id de tipo documento debe ser mayor a 0")
    private long idTipoDocumento;

    @Min(value = 1, message = "El id de serie documento debe ser mayor a 0")
    private long idSerieDocumento;

    @NotBlank(message = "El número es obligatorio")
    @Size(max = 250, message = "El número no debe superar los 250 caracteres")
    private String numero;

    @NotNull(message = "La fecha de emisión es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaEmision;

    @Size(max = 500, message = "La URL del XML no debe superar los 500 caracteres")
    private String urlXml;

    @Size(max = 500, message = "La URL del PDF no debe superar los 500 caracteres")
    private String urlPdf;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 250, message = "El estado no debe superar los 250 caracteres")
    private String estado;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
