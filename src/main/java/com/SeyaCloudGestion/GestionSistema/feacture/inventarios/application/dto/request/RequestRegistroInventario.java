package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestRegistroDetalleInventario;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RequestRegistroInventario {

    @Min(value = 1, message = "El id del almacen debe ser mayor a 0")
    private long idAlmacen;

    @NotBlank(message = "La observacion es obligatoria")
    @Size(max = 250, message = "La observacion no debe superar los 250 caracteres")
    private String observacion;

    @NotEmpty(message = "Debe registrar al menos un articulo para realizar el inventario")
    private List<RequestRegistroDetalleInventario> detalles;
}
