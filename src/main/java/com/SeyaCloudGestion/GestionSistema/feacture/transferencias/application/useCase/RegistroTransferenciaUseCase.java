package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase.ProcesarFullMovimientoStockUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase.DetallePorCodigoTipoMovimientoUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request.RequestRegistroDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseFullRegistroTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.useCase.RegistroProcesarFullDetalleTransferenciaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestRegistroTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseRegistroTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.services.TransferenciaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroTransferenciaUseCase {

    private final TransferenciaService transferenciaService;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    private final RegistroProcesarFullDetalleTransferenciaUseCase registroProcesarFullDetalleTransferenciaUseCase;
    private final DetallePorCodigoTipoMovimientoUseCase detallePorCodigoTipoMovimientoUseCase;
    private final ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase;
    public RegistroTransferenciaUseCase(
            TransferenciaService transferenciaService, DetalleAlmacenUseCase detalleAlmacenUseCase, RegistroProcesarFullDetalleTransferenciaUseCase registroProcesarFullDetalleTransferenciaUseCase, DetallePorCodigoTipoMovimientoUseCase detallePorCodigoTipoMovimientoUseCase, ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase
    ) {
        this.transferenciaService = transferenciaService;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
        this.registroProcesarFullDetalleTransferenciaUseCase = registroProcesarFullDetalleTransferenciaUseCase;
        this.detallePorCodigoTipoMovimientoUseCase = detallePorCodigoTipoMovimientoUseCase;
        this.procesarFullMovimientoStockUseCase = procesarFullMovimientoStockUseCase;
    }

    public ResponseRegistroTransferencia RegistroTransferencia(RequestRegistroTransferencia request) {
        try {
            //verificar que no sean el mismo
            if (request.getIdAlmacenOrigen() == request.getIdAlmacenDestino()) {
                throw new IllegalArgumentException("El origen y el destino no pueden ser lo mismo.");
            }
            // Almacénes
            ResponseDetalleAlmacen resAlmacenOrigen = detalleAlmacenUseCase.DetalleAlmacenes(request.getIdAlmacenOrigen());
            if (!resAlmacenOrigen.isExito() || resAlmacenOrigen.getAlmacen() == null) {
                throw new IllegalArgumentException("El almacén origen especificado no existe.");
            }
            ResponseDetalleAlmacen resAlmacenDestino = detalleAlmacenUseCase.DetalleAlmacenes(request.getIdAlmacenDestino());
            if (!resAlmacenDestino.isExito() || resAlmacenDestino.getAlmacen() == null) {
                throw new IllegalArgumentException("El almacén desitno especificado no existe.");
            }

            ResponseRegistroTransferencia responseRegistroTransferencia = transferenciaService.RegistroTransferencia(request);
            if (!responseRegistroTransferencia.isExito()) {
                throw new IllegalArgumentException("Error al registrar la cabecera de la Transferencia ");
            }
            //get id venta
            long idTransferenciaGenerado = responseRegistroTransferencia.getIdTransferenciaCabecera();
            //procesamos los detalles ojo aqui solo es la foto
            ResponseFullRegistroTransferenciaDetalle ResponseFullRegistroTransferenciaDetalle =
                    registroProcesarFullDetalleTransferenciaUseCase.RegistroDetalleTransferencia(idTransferenciaGenerado,request.getDetalles(),request.getIdAlmacenOrigen());
            if (!ResponseFullRegistroTransferenciaDetalle.isExito() ) {
                throw new IllegalArgumentException(ResponseFullRegistroTransferenciaDetalle.getMessage());
            }
            //jalar el tipo movimiento
            ResponseDetalleTipoMovimiento responseDetalleTipoMovimiento = detallePorCodigoTipoMovimientoUseCase.DetalleTipoMovimiento(TipoMovimientoKardex.EGRESO_TRANSFERENCIA);
            if (!responseDetalleTipoMovimiento.isExito()|| responseDetalleTipoMovimiento.getTipoMovimiento()==null) {
                throw new IllegalArgumentException("Error al buscar el tipo de movimiento");
            }
            for (RequestRegistroDetalleTransferencia detalle : request.getDetalles()) {
                //vamos a sacar los artiuclos del kardex
                //full stock (movimientoStok->kardex->stock)
                RequestProcesarFullStock stockRequest = new RequestProcesarFullStock();
                stockRequest.setIdArticulo(detalle.getIdArticulo());
                stockRequest.setIdAlmacen(request.getIdAlmacenOrigen());
                stockRequest.setCantidad(detalle.getCantidad());
                stockRequest.setIdTipoMovimiento(responseDetalleTipoMovimiento.getTipoMovimiento().getIdTipoMovimiento());
                stockRequest.setTipoPrimitivo(TipoMovimientoKardex.EGRESO_TRANSFERENCIA);
                stockRequest.setObservacion("Salida - Transferencia Nro: " + idTransferenciaGenerado);

                ResponseProcesarFullStock stockResponse = procesarFullMovimientoStockUseCase.procesar(stockRequest);

                if (!stockResponse.isExito()) {
                    throw new IllegalArgumentException("Error de inventario en artículo ID [" + detalle.getIdArticulo() + "]: " + stockResponse.getMessage());
                }
            }

            responseRegistroTransferencia.setExito(true);
            if (responseRegistroTransferencia.isExito()) {
            }

            return responseRegistroTransferencia;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroTransferencia response = new ResponseRegistroTransferencia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar la transferencia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroTransferencia response = new ResponseRegistroTransferencia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}