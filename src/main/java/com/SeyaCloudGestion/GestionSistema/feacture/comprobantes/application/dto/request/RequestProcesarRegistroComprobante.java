package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestProcesarRegistroComprobante  {
    //verificar
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idVenta;
    //verificar
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idTipoComprobante;
    //pasa
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idCaja;
    //verificar
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idTipoDocumentoCliente;

    @NotBlank(message = "El número de documento es obligatorio.")
    @Size(min = 8, max = 11, message = "El número de documento debe tener entre 8 y 11 caracteres.")
    private String numeroDocumentoCliente;

    @NotBlank(message = "La razón social o nombre del cliente es obligatorio.")
    private String razonSocialCliente;
}
