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
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseListaTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.useCase.ListaDetalleTransferenciaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.infraestructure.persistence.model.DetalleTransferenciaModel;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestAceptrarTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseAceptarTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.services.TransferenciaService;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.model.EstadoTransferencia;
import org.springframework.stereotype.Component;

@Component
public class AceptarTransferenciaUseCase {

    private final TransferenciaService transferenciaService;
    private final DetalleTransferenciaUseCase detalleTransferenciaUseCase;
    private final ListaDetalleTransferenciaUseCase listaDetalleTransferenciaUseCase;
    private final DetallePorCodigoTipoMovimientoUseCase detallePorCodigoTipoMovimientoUseCase;
    private final ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;

    public AceptarTransferenciaUseCase(
            TransferenciaService transferenciaService,
            DetalleTransferenciaUseCase detalleTransferenciaUseCase, ListaDetalleTransferenciaUseCase listaDetalleTransferenciaUseCase, DetallePorCodigoTipoMovimientoUseCase detallePorCodigoTipoMovimientoUseCase, ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase
    ) {
        this.transferenciaService = transferenciaService;
        this.detalleTransferenciaUseCase = detalleTransferenciaUseCase;
        this.listaDetalleTransferenciaUseCase = listaDetalleTransferenciaUseCase;
        this.detallePorCodigoTipoMovimientoUseCase = detallePorCodigoTipoMovimientoUseCase;
        this.procesarFullMovimientoStockUseCase = procesarFullMovimientoStockUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
    }

    public ResponseAceptarTransferencia AceptarTransferencia(RequestAceptrarTransferencia request) {
        try {
            //validar la tranferencia
            ResponseDetalleTransferencia detalleBD = detalleTransferenciaUseCase.DetalleTransferencia(request.getIdTransferencia());

            if (!detalleBD.isExito() || detalleBD.getTransferencia() == null) {
                throw new IllegalArgumentException("La transferencia especificada no existe.");
            }
            long idAlamcenDestino = detalleBD.getTransferencia().getIdAlmacenDestino();

            ResponseDetalleAlmacen resAlmacenDestino = detalleAlmacenUseCase.DetalleAlmacenes(idAlamcenDestino);
            if (!resAlmacenDestino.isExito() || resAlmacenDestino.getAlmacen() == null) {
                throw new IllegalArgumentException("El almacén desitno especificado no existe.");
            }
            // verificar que bno estea finalizanda osea aceptada
            if (detalleBD.getTransferencia().getEstado() == EstadoTransferencia.FINALIZADO) {
                 throw new IllegalArgumentException("La transferencia ya ha sido aceptada en este almacen previamente.");
            }
            ResponseListaTransferenciaDetalle listaBDDetalleTransferencia =listaDetalleTransferenciaUseCase.ListaDetalleTransferencia(request.getIdTransferencia());
            if (!listaBDDetalleTransferencia.isExito() || listaBDDetalleTransferencia.getDetalles() == null) {
                throw new IllegalArgumentException("Error transferencia especificada no contiene detalles.");
            }
            //jalar el tipo movimiento
            ResponseDetalleTipoMovimiento responseDetalleTipoMovimiento = detallePorCodigoTipoMovimientoUseCase.DetalleTipoMovimiento(TipoMovimientoKardex.INGRESO_TRANSFERENCIA);
            if (!responseDetalleTipoMovimiento.isExito()|| responseDetalleTipoMovimiento.getTipoMovimiento()==null) {
                throw new IllegalArgumentException("Error al buscar el tipo de movimiento");
            }

            //procesamos los detalles para el kardedx
            for (DetalleTransferenciaModel detalle : listaBDDetalleTransferencia.getDetalles()) {
                //vamos a sacar los artiuclos del kardex
                //full stock (movimientoStok->kardex->stock)
                RequestProcesarFullStock stockRequest = new RequestProcesarFullStock();
                stockRequest.setIdArticulo(detalle.getIdArticulo());
                stockRequest.setIdAlmacen(idAlamcenDestino);
                stockRequest.setCantidad(detalle.getCantidad());
                stockRequest.setCostoUnitario(detalle.getCostoUnitario());
                stockRequest.setIdTipoMovimiento(responseDetalleTipoMovimiento.getTipoMovimiento().getIdTipoMovimiento());
                stockRequest.setTipoPrimitivo(TipoMovimientoKardex.INGRESO_TRANSFERENCIA);
                stockRequest.setObservacion("Ingreso - Transferencia Nro: " + detalleBD.getTransferencia().getIdTransferencia());

                ResponseProcesarFullStock stockResponse = procesarFullMovimientoStockUseCase.procesar(stockRequest);

                if (!stockResponse.isExito()) {
                    throw new IllegalArgumentException("Error de inventario en artículo ID [" + detalle.getIdArticulo() + "]: " + stockResponse.getMessage());
                }
            }
            //se pone como aceptada
            ResponseAceptarTransferencia response = transferenciaService.EditarEstadoTransferencia(request);
            response.setExito(true);
            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseAceptarTransferencia response = new ResponseAceptarTransferencia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al aceptar la transferencia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseAceptarTransferencia response = new ResponseAceptarTransferencia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
