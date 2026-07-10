package com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.request.RequestRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroDetallePago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.CondicionPago;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RequestRegistroVenta  {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idCliente;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idCaja;

    @NotNull(message = "El ID del tipo de comprobante es obligatorio.")
    private Long idTipoComprobante;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idTipoMovimiento ;

    @NotNull(message = "La condicion de pago es obligatoria")
    private CondicionPago condicionPago;


 /*
    @PositiveOrZero(message = "El sub total no puede ser negativo")
    private double subTotal;

    @PositiveOrZero(message = "El impuesto no puede ser negativo")
    private double impuesto;

    @PositiveOrZero(message = "El total no puede ser negativo")
    private double total;

  */
    @NotEmpty(message = "Debe registrar al menos un producto para realizar la venta")
    private List<RequestRegistroDetalleVenta> detalles;

    private List<RequestRegistroDetallePago> detallesPago;
}