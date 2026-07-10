package com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.CondicionPago;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class RequestRegistroCompra {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idProveedor;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacen;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idTipoComprobante;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idTipoMovimiento ;

    @NotBlank(message = "La serie del comprobante es obligatoria.")
    @Size(min = 1, max = 10, message = "La serie debe tener entre 1 y 10 caracteres.")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "La serie solo puede contener letras, números y guiones.")
    private String serieComprobante;

    @NotBlank(message = "El número del comprobante es obligatorio.")
    @Size(min = 1, max = 20, message = "El número debe tener entre 1 y 20 caracteres.")
    @Pattern(regexp = "^[0-9-]+$", message = "El número solo puede contener números y guiones.")
    private String numeroComprobante;

    @DecimalMin(value = "0.0", message = "El subtotal no puede ser negativo.")
    private double subTotal;

    @DecimalMin(value = "0.0", message = "El impuesto no puede ser negativo.")
    private double impuesto;

    @DecimalMin(value = "0.0", message = "El total no puede ser negativo.")
    private double total;

    @NotNull(message = "La condicion de pago es obligatoria")
    private CondicionPago condicionPago;

    @NotEmpty(message = "Debe registrar al menos un producto para realizar la compra")
    private List<RequestRegistroDetalleCompra> detalles;

}
