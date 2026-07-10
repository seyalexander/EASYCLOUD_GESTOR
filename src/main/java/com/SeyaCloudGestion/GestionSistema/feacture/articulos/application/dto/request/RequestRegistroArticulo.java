package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroArticulo {
    @Size(max = 500, message = "La URL de la imagen es demasiado larga")
    private String imagenUrl;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String descripcion;

    @NotBlank(message = "El código del artículo es obligatorio")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String codigoArticulo;

    @Size(max = 50, message = "Máximo 50 caracteres")
    private String codigoBarras;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor a 0")
    private float precioVenta;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;

    @Min(value = 1, message = "Debe seleccionar una familia válida")
    private long isFamilia;

    @Min(value = 1, message = "Debe seleccionar una subfamilia válida")
    private long idSubFamilia;

    @Min(value = 1, message = "Debe seleccionar una unidad de medida")
    private long  idUnidadMedida;

    @DecimalMin(value = "0.0", inclusive = false, message = "El costo de compra debe ser mayor a 0")
    private float costoCompra;

    @DecimalMin(value = "0.0", message = "El stock mínimo no puede ser negativo")
    private float stockMinimo;

    @Min(value = 1, message = "Debe seleccionar una marca válida")
    private long idMarca;

}
