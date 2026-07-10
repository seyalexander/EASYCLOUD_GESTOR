package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestDetalleKardex {
    @Min(value = 1, message = "El id del articulo ser mayor a 0")
    private long idArticulo;

    @Min(value = 1, message = "El id del almacen ser mayor a 0")
    private long idAlmacen;

}
