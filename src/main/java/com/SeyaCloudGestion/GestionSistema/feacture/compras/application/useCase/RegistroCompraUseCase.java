package com.SeyaCloudGestion.GestionSistema.feacture.compras.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestRegistroCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseRegistroCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.services.CompraService;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.RequestRegistroCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseRegistroCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.useCase.RegistroCuentasPorPagarUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response.ResponseRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.useCase.RegistroDetalleCompraUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase.ProcesarFullMovimientoStockUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseDetalleProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.useCase.DetalleProveedorUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase.DetalleTipoComprobanteUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.CondicionPago;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;

@Component
public class RegistroCompraUseCase {

    private final CompraService compraService;
    private final DetalleProveedorUseCase detalleProveedorUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    private final DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase;
    private final RegistroDetalleCompraUseCase registroDetalleCompraUseCase;
    private final ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase;
    private final RegistroCuentasPorPagarUseCase registroCuentasPorPagarUseCase;

    public RegistroCompraUseCase(CompraService compraService, DetalleProveedorUseCase detalleProveedorUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase, DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase, RegistroDetalleCompraUseCase registroDetalleCompraUseCase, ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase, RegistroCuentasPorPagarUseCase registroCuentasPorPagarUseCase) {
        this.compraService = compraService;
        this.detalleProveedorUseCase = detalleProveedorUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
        this.detalleTipoComprobanteUseCase = detalleTipoComprobanteUseCase;
        this.registroDetalleCompraUseCase = registroDetalleCompraUseCase;
        this.procesarFullMovimientoStockUseCase = procesarFullMovimientoStockUseCase;
        this.registroCuentasPorPagarUseCase = registroCuentasPorPagarUseCase;
    }
    @Transactional("sqlServerTransactionManager")
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
            if (!response.isExito()) {
                throw new IllegalArgumentException("Error al registrar la cabecera de la venta ");
            }
            //get id venta
            long idCompraGenerado = response.getIdCompra();
            System.out.println("ID COMPRA = " + idCompraGenerado);
            //for detalles
            for (RequestRegistroDetalleCompra detalle : request.getDetalles()) {
                //setDetalle
                ResponseRegistroDetalleCompra detalleResponse = registroDetalleCompraUseCase.registrarDetalleCompra(idCompraGenerado,detalle, request.getIdAlmacen());
                if (!detalleResponse.isExito()) {
                    throw new IllegalArgumentException("Error al registrar la línea de detalle para el artículo ID [" + detalle.getIdArticulo() + "]: " + detalleResponse.getMessage());
                }

                //full stock (movimientoStok->kardex->stock)
                RequestProcesarFullStock stockRequest = new RequestProcesarFullStock();
                stockRequest.setIdArticulo(detalle.getIdArticulo());
                stockRequest.setIdAlmacen(request.getIdAlmacen());
                stockRequest.setCantidad(detalle.getCantidad());
                stockRequest.setCostoUnitario(detalle.getCostoUnitario());
                stockRequest.setIdTipoMovimiento(request.getIdTipoMovimiento());
                stockRequest.setObservacion("Entrada - Compra Nro: " + idCompraGenerado);

                ResponseProcesarFullStock stockResponse = procesarFullMovimientoStockUseCase.procesar(stockRequest);

                if (!stockResponse.isExito()) {
                    throw new IllegalArgumentException("Error de inventario en artículo ID [" + detalle.getIdArticulo() + "]: " + stockResponse.getMessage());
                }
            }
            //pago
            RequestRegistroCuentasPorPagar requestCuentaPorPagar = new RequestRegistroCuentasPorPagar();
            requestCuentaPorPagar.setIdCompra(idCompraGenerado);
            requestCuentaPorPagar.setMontoPendiente(request.getTotal());
            requestCuentaPorPagar.setFechaVencimiento(LocalDateTime.now().plusDays(request.getCondicionPago() == CondicionPago.CREDITO ? 30 : 0));

            ResponseRegistroCuentasPorPagar registrarCuentaPorPagar = registroCuentasPorPagarUseCase.registrarCuentaPorPagar(requestCuentaPorPagar);
            if (!registrarCuentaPorPagar.isExito()) {
                throw new IllegalArgumentException("Error al registrar la cuenta por pagar: " + registrarCuentaPorPagar.getMessage());
            }
            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            ResponseRegistroCompra response = new ResponseRegistroCompra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            String mensajeError = "Error inesperado al registrar la compra: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroCompra response = new ResponseRegistroCompra();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
