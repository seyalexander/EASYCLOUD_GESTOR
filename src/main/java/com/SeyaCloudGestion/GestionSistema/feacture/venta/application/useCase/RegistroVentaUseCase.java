package com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseDetalleCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.useCase.DetalleClienteUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.request.RequestRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.useCase.RegistroDetalleVentaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase.ProcesarFullMovimientoStockUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase.DetalleTipoMovimientoUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase.DetalleTurnoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestRegistroVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseRegistroVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.services.VentaService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegistroVentaUseCase {

    private final VentaService ventaService;
    private final ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase;
    private final DetalleClienteUseCase detalleClienteUseCase;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;
    private final RegistroDetalleVentaUseCase registroDetalleVentaUseCase;
    private final DetalleTipoMovimientoUseCase detalleTipoMovimientoUseCase;
    public RegistroVentaUseCase(
            VentaService ventaService,
            ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase, DetalleClienteUseCase detalleClienteUseCase, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase, RegistroDetalleVentaUseCase registroDetalleVentaUseCase, DetalleTipoMovimientoUseCase detalleTipoMovimientoUseCase
    ) {
        this.ventaService = ventaService;
        this.procesarFullMovimientoStockUseCase = procesarFullMovimientoStockUseCase;
        this.detalleClienteUseCase = detalleClienteUseCase;
        this.detalleTurnoCajaUseCase = detalleTurnoCajaUseCase;
        this.registroDetalleVentaUseCase = registroDetalleVentaUseCase;
        this.detalleTipoMovimientoUseCase = detalleTipoMovimientoUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseRegistroVenta RegistroVenta(RequestRegistroVenta request) {
        try {
            //cliente
            ResponseDetalleCliente detalleBDcli = detalleClienteUseCase.DetalleCliente(request.getIdCliente());

            if (!detalleBDcli.isExito() || detalleBDcli.getCliente() == null) {
                throw new IllegalArgumentException("El cliente no existe.");
            }
            //turno caja
            ResponseDetalleTurnoCaja detalleBDturnoCaja = detalleTurnoCajaUseCase.DetalleTurnoCaja(request.getIdTurnoCaja());

            if (!detalleBDturnoCaja.isExito() || detalleBDturnoCaja.getTurnoCaja() == null) {
                throw new IllegalArgumentException("El turno caja no existe.");
            }
            //id tipo movimineot
            ResponseDetalleTipoMovimiento detalleBDTiMov =   detalleTipoMovimientoUseCase.DetalleTipoMovimiento(request.getIdTipoMovimiento());
            if (!detalleBDTiMov.isExito() || detalleBDTiMov.getTipoMovimiento() == null) {
                throw new IllegalArgumentException("El tipo de movimiento solicitado no existe.");
            }
            // get articulos
            if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
                throw new IllegalArgumentException("No se puede registrar una venta sin detalles de artículos.");
            }

            // registrar cabecera
            //datos
            double sumaTotalVenta = 0.0;
            //for inicial para insertar datos primarios en la cabecera
            for (RequestRegistroDetalleVenta detalle : request.getDetalles()) {
                double subTotalLinea = detalle.getCantidad() * detalle.getPrecioUnitario();
                double dineroDescuento = subTotalLinea * detalle.getDescuento();
                double totalLinea = subTotalLinea - dineroDescuento;

                sumaTotalVenta += totalLinea;
            }
            double calculoSubTotal = sumaTotalVenta / 1.18;
            double calculoImpuesto = sumaTotalVenta - calculoSubTotal;

            ResponseRegistroVenta response = ventaService.RegistroVenta(request,calculoSubTotal,calculoImpuesto,sumaTotalVenta);

            if (!response.isExito()) {
                throw new IllegalArgumentException("Error al registrar la cabecera de la venta ");
            }
            //get id venta
            long idVentaGenerado = response.getIdVenta();

            // full detalles
            for (RequestRegistroDetalleVenta detalle : request.getDetalles()) {
                detalle.setIdVenta(idVentaGenerado);
                //setDetalle
                ResponseRegistroDetalleVenta detalleResponse = registroDetalleVentaUseCase.registrarDetalleVenta(detalle);
                if (!detalleResponse.isExito()) {
                    throw new IllegalArgumentException("Error al registrar la línea de detalle para el artículo ID [" + detalle.getIdArticulo() + "]: " + detalleResponse.getMessage());
                }

                //full stock (movimientoStok->kardex->stock)
                RequestProcesarFullStock stockRequest = new RequestProcesarFullStock();
                stockRequest.setIdArticulo(detalle.getIdArticulo());
                stockRequest.setIdAlmacen(detalle.getIdAlmacen());
                stockRequest.setCantidad(detalle.getCantidad());
                stockRequest.setIdTipoMovimiento(request.getIdTipoMovimiento());
                stockRequest.setObservacion("Salida - Venta Nro: " + idVentaGenerado);

                ResponseProcesarFullStock stockResponse = procesarFullMovimientoStockUseCase.procesar(stockRequest);

                if (!stockResponse.isExito()) {
                    throw new IllegalArgumentException("Error de inventario en artículo ID [" + detalle.getIdArticulo() + "]: " + stockResponse.getMessage());
                }
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroVenta response = new ResponseRegistroVenta();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar las ventas: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroVenta response = new ResponseRegistroVenta();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}