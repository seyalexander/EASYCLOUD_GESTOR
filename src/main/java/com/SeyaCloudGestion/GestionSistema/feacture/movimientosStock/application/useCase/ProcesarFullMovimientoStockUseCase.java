package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestRegistroKardexRecortado;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseRegistroKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase.DetalleKardexUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase.RegistroKardexUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestRegistroMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseRegistroMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestEditarAllSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestRegistroSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseEditarAllSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseRegistroSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase.DetalleSotckUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase.EdicionSotckUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase.RegistroSotckUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase.DetalleTipoMovimientoUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
public class ProcesarFullMovimientoStockUseCase {
    private final RegistroMovimientoStockUseCase registroMovimientoUseCase;
    //kardex
    private final RegistroKardexUseCase registroKardexUseCase;
    private final DetalleKardexUseCase detalleKardexUseCase;
    //stocks
    private final DetalleSotckUseCase detalleSotckUseCase;
    private final RegistroSotckUseCase registroSotckUseCase;
    private final EdicionSotckUseCase edicionSotckUseCase;
    //tipoMovimiento
    private final DetalleTipoMovimientoUseCase detalleTipoMovimientoUseCase;
    public ProcesarFullMovimientoStockUseCase(
            RegistroMovimientoStockUseCase registroMovimientoUseCase,
            RegistroKardexUseCase registroKardexUseCase,
            DetalleKardexUseCase detalleKardexUseCase,
            DetalleSotckUseCase detalleSotckUseCase,
            RegistroSotckUseCase registroSotckUseCase,
            EdicionSotckUseCase edicionSotckUseCase,
            DetalleTipoMovimientoUseCase detalleTipoMovimientoUseCase) {
        this.registroMovimientoUseCase = registroMovimientoUseCase;
        this.registroKardexUseCase = registroKardexUseCase;
        this.detalleKardexUseCase = detalleKardexUseCase;
        this.detalleSotckUseCase = detalleSotckUseCase;
        this.registroSotckUseCase = registroSotckUseCase;
        this.edicionSotckUseCase = edicionSotckUseCase;
        this.detalleTipoMovimientoUseCase = detalleTipoMovimientoUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseProcesarFullStock procesar(RequestProcesarFullStock request) {

        try {
            //traerl el tipo movimiento
            ResponseDetalleTipoMovimiento detalleBDTiMov =   detalleTipoMovimientoUseCase.DetalleTipoMovimiento(request.getIdTipoMovimiento());
            if (!detalleBDTiMov.isExito() || detalleBDTiMov.getTipoMovimiento() == null) {
                throw new IllegalArgumentException("El tipo de movimiento solicitado no existe.");
            }
            //get entrada
            boolean esEntrada = detalleBDTiMov.getTipoMovimiento().getEsEntrada() == 1;
            //csoto unitario si es venta
            double costoUnitarioParaMovimiento = 0.0;

            TipoMovimientoKardex tipoMov =request.getTipoPrimitivo();
            //solo si es ingreso por compra
            if (esEntrada && tipoMov.equals(TipoMovimientoKardex.INGRESO_COMPRA)) {
                costoUnitarioParaMovimiento = request.getCostoUnitario();
            } else {
                //Aqui cae venta, ajuste salida y ajuste entrada
                ResponseDetalleKardex detalleBDKardex = detalleKardexUseCase.detalleKardex(request.getIdArticulo(), request.getIdAlmacen());

                if (detalleBDKardex.isExito() && detalleBDKardex.getKardex() != null) {
                    double saldoCantidadBD = detalleBDKardex.getKardex().getSaldoCantidad();
                    double saldoCostoBD = detalleBDKardex.getKardex().getSaldoCosto();
                    if (saldoCantidadBD > 0) {
                        costoUnitarioParaMovimiento = saldoCostoBD / saldoCantidadBD;
                    } else {
                        costoUnitarioParaMovimiento = request.getCostoUnitario();
                    }
                } else {
                    // si no hay historial asjuste de ingreso inicial
                    if (esEntrada) {
                        costoUnitarioParaMovimiento = request.getCostoUnitario();
                    } else {
                        throw new IllegalArgumentException("No se puede registrar un egreso sin historial en el Kardex.");
                    }
                }
            }
            //moviminetoStock
            RequestRegistroMovimientoStock requestMov = new RequestRegistroMovimientoStock();
            requestMov.setIdArticulo(request.getIdArticulo());
            requestMov.setIdAlmacen(request.getIdAlmacen());
            requestMov.setIdTipoMovimiento(request.getIdTipoMovimiento());
            requestMov.setCantidad(request.getCantidad());
            requestMov.setCostoUnitario(costoUnitarioParaMovimiento);
            requestMov.setObservacion(request.getObservacion());

            ResponseRegistroMovimientoStock responseMov = registroMovimientoUseCase.RegistroMovimientoStock(requestMov);
            if (!responseMov.isExito()) {
                throw new IllegalArgumentException("Error al registrar el movimiento de stock.");
            }

            //all kardex
            RequestRegistroKardexRecortado requestKardex = new RequestRegistroKardexRecortado();
            requestKardex.setIdArticulo(request.getIdArticulo());
            requestKardex.setIdAlmacen(request.getIdAlmacen());
            requestKardex.setCantidad(request.getCantidad());
            requestKardex.setCostoUnitario(costoUnitarioParaMovimiento);
            requestKardex.setTipoMovimiento(request.getTipoPrimitivo());

            ResponseRegistroKardex responseKardex = registroKardexUseCase.registroKardex(requestKardex);
            if (!responseKardex.isExito()) {
                throw new IllegalArgumentException("Error al registrar en el Kardex: " + responseKardex.getMessage());
            }
            //stock
            ResponseDetalleSotck detalleBDStock = detalleSotckUseCase.DetalleSotck(request.getIdArticulo(),request.getIdAlmacen());
            //si no existe el detalle = que no hay registro (compra inventario o registro invetnario)

            if (!detalleBDStock.isExito() || detalleBDStock.getSotck() == null) {
                // si es entrada
                if (esEntrada) {
                    RequestRegistroSotck requestStock = new RequestRegistroSotck();
                    requestStock.setIdProducto(request.getIdArticulo());
                    requestStock.setIdAlmacen(request.getIdAlmacen());
                    requestStock.setStock(request.getCantidad());

                    ResponseRegistroSotck responseSotck = registroSotckUseCase.RegistroSotck(requestStock);
                    if (!responseSotck.isExito()) {
                        throw new IllegalArgumentException("Error al registrar el saldo de stock inicial.");
                    }
                } else {
                    // es un egreso y no hay registro en la tabla de stocks
                    throw new IllegalArgumentException("Stock insuficiente. El artículo no tiene existencias registradas en este almacén.");
                }
            } else {
                // stock ya existe sumando o restando
                RequestEditarAllSotck requestEditStock = new RequestEditarAllSotck();
                requestEditStock.setIdStockArticulo(detalleBDStock.getSotck().getIdStock());
                requestEditStock.setIdAlmacen(detalleBDStock.getSotck().getIdAlmacen());

                if (esEntrada) {
                    double stockActual = detalleBDStock.getSotck().getStock() + request.getCantidad();
                    requestEditStock.setStock(stockActual);

                    ResponseEditarAllSotck responseEditStock = edicionSotckUseCase.EdicionAllSotck(requestEditStock, request.getIdArticulo());
                    if (!responseEditStock.isExito()) {
                        throw new IllegalArgumentException("Error al editar el stock (Ingreso): " + responseEditStock.getMessage());
                    }
                } else {
                    if (detalleBDStock.getSotck().getStock() < request.getCantidad()) {
                        throw new IllegalArgumentException("Stock insuficiente en el almacén.");
                    }
                    double stockActual = detalleBDStock.getSotck().getStock() - request.getCantidad();
                    requestEditStock.setStock(stockActual);

                    ResponseEditarAllSotck responseEditStock = edicionSotckUseCase.EdicionAllSotck(requestEditStock, request.getIdArticulo());
                    if (!responseEditStock.isExito()) {
                        throw new IllegalArgumentException("Error al editar el stock (Egreso): " + responseEditStock.getMessage());
                    }
                }
            }

            ResponseProcesarFullStock response = new ResponseProcesarFullStock();
            response.setExito(true);
            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            ResponseProcesarFullStock response = new ResponseProcesarFullStock();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            String mensajeError = "Error inesperado al procesar el full service de stock: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseProcesarFullStock response = new ResponseProcesarFullStock();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
