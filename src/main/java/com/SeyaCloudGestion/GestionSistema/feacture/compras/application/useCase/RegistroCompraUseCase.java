package com.SeyaCloudGestion.GestionSistema.feacture.compras.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestRegistroCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseRegistroCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.services.CompraService;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseDetalleComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.useCase.DetalleComprobanteUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.useCase.RegistroDetalleCompraUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseDetalleProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.useCase.DetalleProveedorUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase.DetalleTipoComprobanteUseCase;
import org.springframework.stereotype.Component;

@Component
public class RegistroCompraUseCase {

    private final CompraService compraService;
    private final DetalleProveedorUseCase detalleProveedorUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    private final DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase;
    private final RegistroDetalleCompraUseCase registroDetalleCompraUseCase;
    public RegistroCompraUseCase(CompraService compraService, DetalleProveedorUseCase detalleProveedorUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase, DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase, RegistroDetalleCompraUseCase registroDetalleCompraUseCase) {
        this.compraService = compraService;
        this.detalleProveedorUseCase = detalleProveedorUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
        this.detalleTipoComprobanteUseCase = detalleTipoComprobanteUseCase;
        this.registroDetalleCompraUseCase = registroDetalleCompraUseCase;
    }

    public ResponseRegistroCompra registroCompra(RequestRegistroCompra request) {
        try {
            //provedor
            ResponseDetalleProveedor resProveedor = detalleProveedorUseCase.DetalleProveedores(request.getIdProveedor());
            if (!resProveedor.isExito()|| resProveedor.getProveedor()==null) {
                throw new IllegalArgumentException("El proveedor especificado no existe");
            }
            // Almacén
            ResponseDetalleAlmacen resAlmacen = detalleAlmacenUseCase.DetalleAlmacenes(request.getIdAlmacen());
            if (!resAlmacen.isExito() || resAlmacen.getAlmacen() == null) {
                throw new IllegalArgumentException("El almacén especificado no existe.");
            }
            // Tipo de comprobante
            ResponseDetalleTipoComprobante resTipoComprobante = detalleTipoComprobanteUseCase.DetalleTipoComprobante(request.getIdTipoComprobante());
            if (!resTipoComprobante.isExito() || resTipoComprobante.getTipoCompobante() == null) {
                throw new IllegalArgumentException("El tipo de comprobante especificado no existe.");
            }
            //validaciones de negocio
            // get articulos
            if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
                throw new IllegalArgumentException("No se puede registrar una compra sin artículos.");
            }
            //suma de la compra
            double sumaTotalCompra = 0.0;
            for (var detalle : request.getDetalles()) {
                double subTotalLinea = detalle.getCantidad() * detalle.getCostoUnitario();
                sumaTotalCompra += subTotalLinea;
            }

            double calculoSubTotal = sumaTotalCompra / 1.18;
            double calculoImpuesto = sumaTotalCompra - calculoSubTotal;
            //comprovamos
            if (Math.abs(sumaTotalCompra - request.getTotal()) > 0.05) {
                throw new IllegalArgumentException("El total calculado (" + sumaTotalCompra + ") no coincide con el total enviado (" + request.getTotal() + ").");
            }

            ResponseRegistroCompra response = compraService.RegistroCompra(request,calculoSubTotal, calculoImpuesto, sumaTotalCompra);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroCompra response = new ResponseRegistroCompra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar la compra: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroCompra response = new ResponseRegistroCompra();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}