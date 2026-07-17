package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestRegistrarAjusteInventarioInicial;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseRegistrarInventarioInicalAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase.ProcesarFullMovimientoStockUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase.DetallePorCodigoTipoMovimientoUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegistroAjusteInventarioInicialUseCase {

    private final ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase;
    private final DetallePorCodigoTipoMovimientoUseCase detallePorCodigoTipoMovimientoUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;

    public RegistroAjusteInventarioInicialUseCase(
            ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase,
            DetallePorCodigoTipoMovimientoUseCase detallePorCodigoTipoMovimientoUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase
    ) {
        this.procesarFullMovimientoStockUseCase = procesarFullMovimientoStockUseCase;
        this.detallePorCodigoTipoMovimientoUseCase = detallePorCodigoTipoMovimientoUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseRegistrarInventarioInicalAjuste RegistroInventarioInicial(RequestRegistrarAjusteInventarioInicial request) {
        try {
            // Almacén
            ResponseDetalleAlmacen resAlmacen = detalleAlmacenUseCase.DetalleAlmacenes(request.getIdAlmacen());
            if (!resAlmacen.isExito() || resAlmacen.getAlmacen() == null) {
                throw new IllegalArgumentException("El almacén especificado no existe.");
            }
            // get articulos
            if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
                throw new IllegalArgumentException("No se puede registrar un inventario incial sin artículos.");
            }
            // jalamos el id para el ajuste
            ResponseDetalleTipoMovimiento responseTipoMov = detallePorCodigoTipoMovimientoUseCase.DetalleTipoMovimiento(TipoMovimientoKardex.INGRESO_AJUSTE);

            if (!responseTipoMov.isExito() || responseTipoMov.getTipoMovimiento() == null) {
                throw new IllegalArgumentException("No se encontró configurado el tipo de movimiento INGRESO_AJUSTE.");
            }

            long idTipoMovimientoDb = responseTipoMov.getTipoMovimiento().getIdTipoMovimiento();

            for (RequestRegistroDetalleCompra item : request.getDetalles()) {

                RequestProcesarFullStock requestStockCore = new RequestProcesarFullStock();
                requestStockCore.setIdArticulo(item.getIdArticulo());
                requestStockCore.setIdAlmacen(request.getIdAlmacen());
                requestStockCore.setCantidad(item.getCantidad());
                requestStockCore.setCostoUnitario(item.getCostoUnitario());

                requestStockCore.setTipoPrimitivo(TipoMovimientoKardex.INGRESO_AJUSTE);
                requestStockCore.setIdTipoMovimiento(idTipoMovimientoDb);
                requestStockCore.setObservacion("Carga de stock inicial ingreso de datos");

                ResponseProcesarFullStock responseMov = procesarFullMovimientoStockUseCase.procesar(requestStockCore);
                if (!responseMov.isExito()) {
                    throw new IllegalArgumentException(
                            "Error al cargar stock inicial " + item.getIdArticulo() + ": " + responseMov.getMessage()
                    );
                }
            }

            ResponseRegistrarInventarioInicalAjuste response = new ResponseRegistrarInventarioInicalAjuste();
            response.setExito(true);
            if(response.isExito()){


            }
            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistrarInventarioInicalAjuste response = new ResponseRegistrarInventarioInicalAjuste();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el inventario inicial: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistrarInventarioInicalAjuste response = new ResponseRegistrarInventarioInicalAjuste();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}